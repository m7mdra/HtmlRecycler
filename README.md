# HtmlRecylcer
Converts a simple html page in to A `RecyclerView` with Native android widgets powered by [Jsoup library](https://jsoup.org/) and inspired by [Medium Textview](https://github.com/angebagui/medium-textview/)

> ***Note this is under development and  unstable***

### demo
[here](https://github.com/m7mdra/HtmlRecylcer/blob/master/app-debug.apk) 
## Currently supported html elements:

 - [x] Paragraph 
 - [x] H1...H6
 - [x] Image
 - [x] Video
 - [x] Audio
 - [x] Ordered List
 - [x]  Unordered List
 - [x]  Description List
 - [x]  Anchor Link
 - [x]  IFrame
 - [ ] Table
 - [ ] DIV 

## Implement in your project ? 

    HtmlRecycler.Builder(this)  
        .setSource(StringSource(Data.data))  
        .setAdapter(DefaultElementsAdapter(this) { element, i, view->  
	  if (element is ImageElement)  
                supportFragmentManager.beginTransaction().apply {  
	  replace(R.id.fragment_layout,ImageFragment.newInstance(element.ImageUrl))  
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)  
                    commit()  
                    addToBackStack("")  
                }  
	 }).setRecyclerView(recyclerView)  
        .build()
the above code uses the existing implementation of `DefaultElementsAdapter` which `extends` `ElementsAdapter` class which inherently is a `RecylcerView Adpater`  
the `DefaultElementsAdapter` uses a layout resources files defined by me but they not styled probably and are very buggy(especially the video, audio and iframe ones)

want to create your own adapter ? just simply extend `ElementsAdapter`
and override methods

      
	class BetterImplementationThanTheAuthorsAdapter : ElementsAdapter() {  
  
    override fun onCreateElement(parent: ViewGroup, elementType: ElementType): RecyclerView.ViewHolder {  
        when (elementType) {  
            ElementType.Paragraph -> {  
                return ParagraphViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragarph, parent, false))  
            }  
            //.  
			// .
			// .
			//other elements defined here  }  
    }  
  
    override fun onBindElement(holder: RecyclerView.ViewHolder, position: Int) {  
        val element = elements[position] //current element  
	  if (holder is ParagraphViewHolder){  
            val paragraphElement = element as ParagraphElement  
            holder.paragraphText.text= paragraphElement.text  
		  }  
	    }  
    }
after that replace your adapter with the default implementation

    HtmlRecycler.Builder(this)  
        .setSource(StringSource(Data.data))  
        .setAdapter(BetterImplementationThanTheAuthorsAdapter()) // this is a custom adapter  
	    .setRecyclerView(recyclerView)  
        .build()
### How to add Data

Data can come from different sources , the library support the following

 - [x] Assets
 - [x] File
 - [x] String
 - [x] Network (runs on `UI thread` by default so you have to run it on different thread or write your own Source Implementation )
### Write your own source ?
simply implement the `Source` interface which will return a `Document` of the parsed Source

    class FileSource(val file: File) : Source {  
    override fun get(): Document {  
        return Jsoup.parse(file, "UTF-8")  
	    }  
    }
## attach Click listeners on elements?
in `DefaultElemetsAdapter` class at line [#27](https://github.com/m7mdra/HtmlRecylcer/blob/master/htmlrecycler/src/main/java/m7mdra/com/htmlrecycler/adapter/DefaultElementsAdapter.kt#L27) l i defined a [higher-order-function](https://kotlinlang.org/docs/reference/lambdas.html#higher-order-functions) in the constructor method (which dose the same as defining an interface) and on line [#75](https://github.com/m7mdra/HtmlRecylcer/blob/master/htmlrecycler/src/main/java/m7mdra/com/htmlrecycler/adapter/DefaultElementsAdapter.kt#L75) we envoke the method passing our element and the position of the clicked view.

## TODO list: 

 - [ ] Define a standard Layout styling
 - [ ] allow `NetworkSource` to run on `UI thread` 
 - [ ] Support Table and Div elements
 - [ ] Test Element Extractors for different data sets
 - [ ] other thing that i come up with

PR are **welcome** just use crtl+alt+L or (command + alt+L for mac ... idk if right) after every time your finish write code to **format it**
