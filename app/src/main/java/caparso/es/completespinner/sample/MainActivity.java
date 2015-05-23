package caparso.es.completespinner.sample;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import caparso.es.completespinner.CompleteSpinner;
import caparso.es.completespinner.sample.vo.SpinnerVO;
import caparso.es.completespinner.spinnermode.SpinnerMode;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner with custom view
        AutoCompleteTextView actSpinner = (AutoCompleteTextView) findViewById(R.id.act_spinner);
        LinearLayout llSpinnerContainer = (LinearLayout) findViewById(R.id.ll_spinner_container);
        final CompleteSpinner<SpinnerVO> completeSpinner = new CompleteSpinner<SpinnerVO>(this, SpinnerMode.MODE_POPUP);
        completeSpinner.setView(llSpinnerContainer, actSpinner);
        completeSpinner.setAdapter(getAdapter());

        // Textview to remove selected item
        TextView tvRemoveSelected = (TextView) findViewById(R.id.tv_delete);
        tvRemoveSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeSpinner.removeSelectedItem();
            }
        });

        // Textview to get selected item
        TextView tvGetSelected = (TextView) findViewById(R.id.tv_selected);
        tvGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (completeSpinner.getSelectedItem() != null) {
                    SpinnerVO sprinnerVO = completeSpinner.getSelectedItem();
                    String selectedText = sprinnerVO.getId() + ", " + sprinnerVO.getTitulo();
                    Toast.makeText(MainActivity.this, selectedText, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Default spinner
        LinearLayout llSpinnerContainer2 = (LinearLayout) findViewById(R.id.ll_spinner_container2);
        CompleteSpinner completeSpinner2 = new CompleteSpinner(this, SpinnerMode.MODE_DROPDOWN);
        completeSpinner2.setAdapter(getAdapter());
        llSpinnerContainer2.addView(completeSpinner2.getView());

    }

    private ArrayAdapter getAdapter() {
        final ArrayAdapter<SpinnerVO> arrayAdapter = new ArrayAdapter<SpinnerVO>(
                this,
                android.R.layout.select_dialog_item);
        arrayAdapter.add(new SpinnerVO(0, "King in the north"));
        arrayAdapter.add(new SpinnerVO(1, "Lanister"));
        arrayAdapter.add(new SpinnerVO(2, "Kalesi"));
        arrayAdapter.add(new SpinnerVO(3, "Peter Gregory"));
        arrayAdapter.add(new SpinnerVO(4, "Gavin Belson"));
        arrayAdapter.add(new SpinnerVO(5, "Gilfoyle"));
        arrayAdapter.add(new SpinnerVO(6, "Ragnar Lothbrok"));
        arrayAdapter.add(new SpinnerVO(7, "Jared"));
        arrayAdapter.add(new SpinnerVO(8, "Dinesh"));
        return arrayAdapter;
    }

}
