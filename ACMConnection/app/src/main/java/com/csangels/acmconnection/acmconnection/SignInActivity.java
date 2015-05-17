package com.csangels.acmconnection.acmconnection;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.csangels.acmconnection.acmconnection.Chat.ApplicationSingleton;
import com.csangels.acmconnection.acmconnection.Chat.DialogsActivity;
import com.csangels.acmconnection.acmconnection.helper.DataHolder;
import com.csangels.acmconnection.acmconnection.utils.DialogUtils;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.SmackException;

import java.util.List;

public class SignInActivity extends BaseActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private QBChatService chatService;
    private QBUser qbUser;
    private static String USER_LOGIN;
    private static String USER_PASSWORD;
    static final int AUTO_PRESENCE_INTERVAL_IN_SECONDS = 30;


    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        loginEditText = (EditText) findViewById(R.id.login_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
    }

    public void signIn(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                progressDialog.show();

                USER_LOGIN = loginEditText.getText().toString();
                USER_PASSWORD = passwordEditText.getText().toString();

                // Sign in application with user
                //
                qbUser = new QBUser(loginEditText.getText().toString(), passwordEditText.getText().toString());
                QBUsers.signIn(qbUser, new QBEntityCallbackImpl<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        progressDialog.hide();

                        setResult(RESULT_OK);

                        DataHolder.getDataHolder().setSignInQbUser(qbUser);
                        // password does not come, so if you want use it somewhere else, try something like this:
                        DataHolder.getDataHolder().setSignInUserPassword(passwordEditText.getText().toString());
                        DialogUtils.showLong(context, getResources().getString(R.string.user_successfully_sign_in));

                        startChatActivity();
                    }

                    @Override
                    public void onError(List<String> errors) {
                        progressDialog.hide();
                        DialogUtils.showLong(context, errors.get(0));
                    }
                });

                break;
        }
    }

    public void signUp(View view) {
        switch (view.getId()) {
            case R.id.sign_up_button:
                progressDialog.show();

                USER_LOGIN = loginEditText.getText().toString();
                USER_PASSWORD = passwordEditText.getText().toString();

                // Sign Up user
                //
                qbUser = new QBUser();
                qbUser.setLogin(loginEditText.getText().toString());
                qbUser.setPassword(passwordEditText.getText().toString());
                QBUsers.signUpSignInTask(qbUser, new QBEntityCallbackImpl<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        progressDialog.hide();

                        DataHolder.getDataHolder().addQbUserToList(qbUser);
                        DataHolder.getDataHolder().setSignInQbUser(qbUser);
                        DataHolder.getDataHolder().setSignInUserPassword(passwordEditText.getText().toString());

                        startChatActivity();
                    }

                    @Override
                    public void onError(List<String> strings) {
                        progressDialog.hide();
                        DialogUtils.showLong(context, strings.get(0));
                    }
                });

                break;
        }
    }

    private void startChatActivity() {
        QBChatService.setDebugEnabled(true);
        if (!QBChatService.isInitialized()) {
            QBChatService.init(this);
        }
        chatService = QBChatService.getInstance();

        // create QB user
        //
        final QBUser user =  new QBUser();
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);

        QBAuth.createSession(user, new QBEntityCallbackImpl<QBSession>(){
            @Override
            public void onSuccess(QBSession session, Bundle args) {

                // save current user
                //
                user.setId(session.getUserId());
                ((ApplicationSingleton)getApplication()).setCurrentUser(user);

                // login to Chat
                //
                loginToChat(user);
            }

            @Override
            public void onError(List<String> errors) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SignInActivity.this);
                dialog.setMessage("create session errors: " + errors).create().show();
            }
        });
    }

    private void loginToChat(final QBUser user){

        chatService.login(user, new QBEntityCallbackImpl() {
            @Override
            public void onSuccess() {

                // Start sending presences
                //
                try {
                    chatService.startAutoSendPresence(AUTO_PRESENCE_INTERVAL_IN_SECONDS);
                } catch (SmackException.NotLoggedInException e) {
                    e.printStackTrace();
                }


                // go to Dialogs screen
                //
                Intent intent = new Intent(SignInActivity.this, DialogsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(List errors) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SignInActivity.this);
                dialog.setMessage("chat login errors: " + errors).create().show();
            }
        });
    }
}