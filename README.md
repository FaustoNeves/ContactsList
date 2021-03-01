# MyPeople

Many thanks to [Kotlin MVVM tutorial](https://www.youtube.com/watch?v=v2yocpEcE_g).
This app illustrates how to create a register app (saving data on local database, no remote work needed) using MVVM pattern (live data and binding data).
Now, what are the benefits of binding data with live data? In general, its less logic in the activity class, but there is a ton of boilerplate code in the activity.
We need to instanciante view model class itself and view model factory. Why do we need to instanciante view model factory? Well, view model classes don't take any arguments, and
its common practice to initialize our view model classes with some source of data classes (repositories, in most cases!). For this, we need to create our own view model initializer
wich is just a boiler plate code. You can get all this from here [Google's official site for MVVMM](https://developer.android.com/jetpack/guide?hl=en-us).
About binding data, its the same thing. Less code in the activity, but much more code in the XML file. Thats right, we'll write code in our XML file. 
To bind data, we can do like this:

Replace the usual method ```setContentView(R.layout.activity_main)``` to ```binding = DataBindingUtil.setContentView(this, R.layout.activity_main)```
The variable binding is initialized like this ```private lateinit var binding: ActivityMainBinding```
Now, notice the name ActivityMainBinding? Its auto generated, we don't define it (as far as I know).

In XML, we need to wrap ALL our code with a ```<layout></layout>``` tag. After this, we create a ```<data><variable/></data>``` tag. 
It must be something like this:
```
<data>

        <variable
            name="viewModel"
            type="br.com.fausto.mypeople.ui.viewmodel.SubscriberVM" />
</data>
```

You see the name "viewModel"? Our variable binding in the main activity will connect these variables in the XML file with our view model class attributes through this
name field, like this:
```binding.viewModel = subscriberViewModel```

And, these fields (that we are binding from XML to view model class) will be like this:
```
<com.google.android.material.textfield.TextInputEditText
                android:id="@+id/textInputNameB"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:hint="@string/name"
                android:text="@={viewModel.inputName}"
                />
```
See the attribute ```android:text="@={viewModel.inputName}```? It will be directly connected with a live data attribute in our view model class.
Got it? We are done with the XML part, now lets go to our view model class.

Take a look in this attribute:
```
@Bindable
val inputName = MutableLiveData<String>()
```
Its connected with our XML file field. Its simple as that. It will retrieve the inputs from the view. Its very simple, and the rest is just observed in the main activity.


I hope you understand it! Thank you very much, bye!
