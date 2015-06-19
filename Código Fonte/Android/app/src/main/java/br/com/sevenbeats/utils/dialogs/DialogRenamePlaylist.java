package br.com.sevenbeats.utils.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.sevenbeats.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DialogRenamePlaylist extends DialogFragment {

    private Dialog mDialog;
    @InjectView(R.id.playlist_dialog_text) EditText text;

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_AppCompat_Light));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_playlist_rename, null);
        ButterKnife.inject(this, view);
        builder.setView(view);
        mDialog = builder.create();
        return mDialog;
    }

    @OnClick(R.id.playlist_dialog_rename) public void onRenameClick(){
        if(text.getText().length() > 0){
            mDialog.cancel();
        }
    }

    @OnClick(R.id.playlist_dialog_cancel) public void onCancelClick(){
        mDialog.cancel();
    }
}