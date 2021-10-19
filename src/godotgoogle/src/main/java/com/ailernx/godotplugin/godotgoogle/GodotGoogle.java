package com.ailernx.godotplugin.godotgoogle;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Set;


public class GodotGoogle extends GodotPlugin {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "GoogleLogin";

    private GoogleSignInAccount mGoogleSignInAccount;
    private GoogleSignInClient mGoogleSignInClient;

    private Activity mActivity;

    public GodotGoogle(Godot godot) {
        super(godot);

        mActivity = godot.getActivity();

        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();

        mGoogleSignInClient = GoogleSignIn.getClient(mActivity, mGoogleSignInOptions);

        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(mActivity);
    }


    @NonNull
    @Override
    public String getPluginName() {
        return getClass().getSimpleName();
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("signed_in"));
        signals.add(new SignalInfo("signin_error", Integer.class));
        signals.add(new SignalInfo("signed_out"));

        return signals;
    }

    @UsedByGodot
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @UsedByGodot
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mGoogleSignInAccount = null;
                        emitSignal("signed_out");
                    }
                });
    }

    @UsedByGodot
    public boolean isSigned() {
        return mGoogleSignInAccount != null;
    }

    @UsedByGodot
    public String getId() {
        return isSigned() ? mGoogleSignInAccount.getId() : null;
    }

    @UsedByGodot
    public String getFirstName() {
        return isSigned() ? mGoogleSignInAccount.getGivenName() : null;
    }

    @UsedByGodot
    public String getDisplayName() {
        return isSigned() ? mGoogleSignInAccount.getDisplayName() : null;
    }

    @UsedByGodot
    public String getPictureUri() {
        return isSigned() ? mGoogleSignInAccount.getPhotoUrl().toString() : null;
    }


    @Override
    public void onMainResume() {
        super.onMainResume();

        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(mActivity);
    }

    @Override
    public void onMainActivityResult(int requestCode, int resultCode, Intent data) {
        super.onMainActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            mGoogleSignInAccount = completedTask.getResult(ApiException.class);

            emitSignal("signed_in");
        } catch (ApiException e) {
            mGoogleSignInAccount = null;

            emitSignal("signin_error", e.getStatusCode());
        }
    }
}
