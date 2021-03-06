CompleteSpinner
=======

A conceptual implementation of Android Spinner that handle **hint text** and **layout customization**. Easy to use.

![enter image description here](http://oi62.tinypic.com/2rfsufq.jpg)

Download
--------

Gradle:

    Coming soon

Maven:

    Coming soon

Considerations and code
--------

CompleteSpinner build the string value of the object shown in the view, using **toString()** method from *java.lang.Object* class. So, it is necesary to override this method in the object class, to get the right String format.
```java
    @Override
    public String toString() {
        return getTitulo();
    }
```
 
 
Spinner layout is compose of: **parent View**, that represents the container of the Spinner, and an **AutoCompleteTextView**, where is going to be shown the selected object. Then, it's possible to add some extra views to customize the Spinner.
```xml
      <LinearLayout
        android:id="@+id/ll_spinner_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@drawable/shape_box_container"
        android:gravity="center"
        android:orientation="horizontal">

        <AutoCompleteTextView
            android:id="@+id/act_spinner"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:background="@android:color/transparent"
            android:padding="10dp"/>

        <ImageView
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:scaleType="centerInside"
            android:src="@mipmap/dropdown_arrow"/>

    </LinearLayout>
```

Finally, an example of Java code.
```java
AutoCompleteTextView actSpinner = (AutoCompleteTextView) findViewById(R.id.act_spinner);
LinearLayout llSpinnerContainer = (LinearLayout) findViewById(R.id.ll_spinner_container);
        
final CompleteSpinner<SpinnerVO> completeSpinner = new CompleteSpinner<SpinnerVO>(this, SpinnerMode.MODE_POPUP);
completeSpinner.setView(llSpinnerContainer, actSpinner);
completeSpinner.setAdapter(getAdapter());
```

Next developments
--------

- Highlight the selected item, in dropdown/popup view.
- Multi selectable spinner.
