package caparso.es.completespinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

import caparso.es.completespinner.spinnermode.SpinnerMode;

public class CompleteSpinner<T> extends View implements View.OnClickListener {

    /**
     * Context of the spinner.
     */
    private Context context;

    /**
     * Container of the spinner view.
     */
    private View view;

    /**
     * TextView inside the spinner container. This view show selected item.
     */
    private AutoCompleteTextView textView;

    /**
     * ListView inside the popup view.
     */
    private ListView listView;

    /**
     * Alert dialog builder to show a popup with a listview.
     */
    private AlertDialog.Builder alertBuilder;

    /**
     * Selected object of the elements list.
     */
    private T selectedItem;

    /**
     * Mode of the spinner. 0: Popup mode, 1: Dropdown mode
     */
    private int spinnerMode;

    /**
     * True if dropdown is just dismissed and false otherwise. JUST FOR API 17+
     */
    private Boolean isDropDownDismissed = false;

    /**
     * Default constructor
     *
     * @param context
     * @param spinnerMode
     */
    public CompleteSpinner(Context context, int spinnerMode) {
        super(context);
        this.context = context;
        this.spinnerMode = spinnerMode;
        buildDefaultSpinnerView();
    }

    /**
     * Set the custom view of the spinner.
     *
     * @param view
     * @param textView
     */
    public void setView(final View view, AutoCompleteTextView textView) {
        prepareAutoCompleteTextView(view, textView);
        this.view = view;
        this.textView = textView;
    }

    /**
     * Set the items adapter of the listview.
     *
     * @param adapter ArrayAdapter of the listview. IT IS REQUIRED THAT THE ADAPTER OBJECTS OVERRIDE TOSTRING() METHOD.
     */
    public void setAdapter(ArrayAdapter<T> adapter) {
        if (spinnerMode == SpinnerMode.MODE_DROPDOWN) {
            buildSpinnerDropDown(adapter);
        } else {
            buildSpinnerPopup(adapter);
        }
        view.setOnClickListener(this);
    }

    /**
     * Invoke when spinner viewgroup is clicked.
     *
     * @param v viewgroup of the spinner.
     */
    @Override
    public void onClick(View v) {
        if (spinnerMode == SpinnerMode.MODE_DROPDOWN) {
            textView.showDropDown();
        } else {
            alertBuilder.show();
        }
    }

    /**
     * Enables a dropdown list of options.
     */
    public void setSpinnerModeDrop() {
        spinnerMode = SpinnerMode.MODE_DROPDOWN;
    }

    /**
     * Enables a popup with a list of options.
     */
    public void setSpinnerModePopup() {
        spinnerMode = SpinnerMode.MODE_POPUP;
    }

    /**
     * Remove the selected item and clear the spinner.
     */
    public void removeSelectedItem() {
        textView.setText("");
        selectedItem = null;
    }

    /**
     * Return the selected item of the spinner.
     *
     * @return selected item
     */
    public T getSelectedItem() {
        return selectedItem;
    }

    /**
     * Set selected item in the spinner.
     *
     * @param selectedItem
     */
    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        textView.setText(selectedItem.toString());
    }

    /**
     * Return the container view of the spinner.
     *
     * @return
     */
    public View getView() {
        return view;
    }

    /**
     * Build a default view for the spinner.
     */
    private void buildDefaultSpinnerView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View defaultView = inflater.inflate(R.layout.spinner_default, null);
        this.view = (LinearLayout) defaultView.findViewById(R.id.ll_spinner_container);
        this.textView = (AutoCompleteTextView) defaultView.findViewById(R.id.act_spinner);
        prepareAutoCompleteTextView(this.view, this.textView);
    }

    /**
     * Build the popup with the items list to show on click event. Just single choice option.
     *
     * @param adapter Popup adapter with the list of options.
     */
    private void buildSpinnerPopup(final ArrayAdapter<T> adapter) {
        alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(R.string.popup_choose_one);
        alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSelectedItem(adapter.getItem(which));
            }
        });
        alertBuilder.setPositiveButton(R.string.popup_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton(R.string.popup_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeSelectedItem();
                dialog.dismiss();
            }
        });
    }

    /**
     * Build dropdown items list to show on click event. Just single choice option.
     *
     * @param adapter Dropdown adapter with the list of options.
     */
    private void buildSpinnerDropDown(final ArrayAdapter<T> adapter) {
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = adapter.getItem(position);
            }
        });
    }

    /**
     * Prepare view elements to build the spinner. AutoCompleteTextView must be not focusable, no suggestion type and perform the viewgroup click.
     */
    private void prepareAutoCompleteTextView(final View view, AutoCompleteTextView autoCompleteTextView) {
        if (autoCompleteTextView.getHint() == null || "".equals(autoCompleteTextView.getHint())) {
            autoCompleteTextView.setHint(context.getString(R.string.spinner_hint_default));
        }
        autoCompleteTextView.setFocusable(false);
        autoCompleteTextView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        autoCompleteTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction() && !isDropDownDismissed) {
                    view.performClick();
                }
                isDropDownDismissed = false;
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
                @Override
                public void onDismiss() {
                    isDropDownDismissed = true;
                }
            });
        }

    }
}
