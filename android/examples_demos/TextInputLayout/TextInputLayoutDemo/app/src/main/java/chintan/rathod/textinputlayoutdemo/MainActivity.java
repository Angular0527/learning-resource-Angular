package chintan.rathod.textinputlayoutdemo;

import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.lang.reflect.Field;

/**
 * @author Chintan Rathod
 */
public class MainActivity extends ActionBarActivity {

    EditText firstName;

    TextInputLayout tilFiratName,tilLastName,tilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName= (EditText) findViewById(R.id.edtFirstName);
        tilFiratName = (TextInputLayout) findViewById(R.id.tilFirstName);
        tilLastName = (TextInputLayout) findViewById(R.id.tilLastName);
        tilEmail = (TextInputLayout)findViewById(R.id.tilEmail);

        setTypefaceToInputLayout(tilFiratName,"KGCamdenMarketScript.ttf");

        setTypefaceToInputLayout(tilLastName,"Adidas.ttf");

        setTypefaceToInputLayout(tilEmail,"Organo.ttf");
    }

    private void setTypefaceToInputLayout(TextInputLayout inputLayout, String typeFace){

        final Typeface tf = Typeface.createFromAsset(getAssets(), typeFace);

        inputLayout.getEditText().setTypeface(tf);
        try {
            // Retrieve the CollapsingTextHelper Field
            final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            collapsingTextHelperField.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
            final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
        } catch (Exception ignored) {
            // Nothing to do
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
