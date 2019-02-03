

# HtmlRecycler
Converts a simple html page into A `RecyclerView` of native android widgets powered by [Jsoup library](https://jsoup.org/) and inspired by [Medium Textview](https://github.com/angebagui/medium-textview/).

### ***This is under development*** 

> ### ***Note*** 
>  This library was design and developed by ME and we use this in our application which depends on a Content Management system and was never intended to replace browsers or act as one. this library simply gave us more control over html page than `WebView`

## Add it to your project
```groovy
dependencies {
    implementation 'com.github.m7mdra:HtmlRecycler:0.1.11'
}
```

This project is distributed using jitpack. Make sure you add it as a maven repository to your `build.gradle`
```groovy
allprojects {
    repositories {
        maven { 
            url 'https://jitpack.io' 
        }
    }
}
```

## Demo
|  |  |
|--|--|
| ![enter image description here](https://raw.githubusercontent.com/m7mdra/HtmlRecycler/master/media/demo1.gif) | ![enter image description here](https://raw.githubusercontent.com/m7mdra/HtmlRecycler/master/media/demo2.gif) |

 - [APK](https://cdn.rawgit.com/m7mdra/HtmlRecycler/d278854a/app/build/outputs/apk/debug/app-debug.apk) 
 - Or simply `git clone` the repository and build the `app` module. 
 

 
## Currently supported html elements
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
 - [x] DIV 

## Implementation
```Kotlin
val networkSource = NetworkSource("https://gist.githubusercontent.com/m7mdra/f22c62bc6941e08064b4fbceb4832a90/raw/ea8574d986635cf214541f1f5702ef37cc731aaf/article.html")  
  
HtmlRecycler.Builder(this@MainActivity)  
    .setSource(networkSource)  
    .setAdapter(DefaultElementsAdapter(this@MainActivity) { element, i, view ->  
    }})
    .setRecyclerView(recyclerView)  
    .setLoadingCallback(object : HtmlRecycler.LoadCallback {  
        override fun onLoadingStart() {  
            progressBar.visibility = View.VISIBLE  
        }  
        override fun onLoaded(document: Document?) {  
            progressBar.visibility = View.GONE
  	}  
    })  
    .build()
```

The above code uses the existing implementation of `DefaultElementsAdapter` which `extends` `ElementsAdapter` class which inherently is a `RecylcerView Adpater` the `DefaultElementsAdapter` uses a layout resources files defined by me but they not styled probably and are very buggy (especially the video, audio and iframe ones).

Want to create your own adapter? just simply extend `ElementsAdapter` and override methods:
```Kotlin
class BetterImplementationThanTheAuthorsAdapter : ElementsAdapter() {    
    override fun onCreateElement(parent: ViewGroup, elementType: ElementType): RecyclerView.ViewHolder {  
        when (elementType) {  
            ElementType.Paragraph -> {  
                return ParagraphViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragarph, parent, false))  
            }
	    // Define other elements here
        }  
    }  
  
    override fun onBindElement(holder: RecyclerView.ViewHolder, position: Int) {  
        val element = elements[position] //current element  
	    if (holder is ParagraphViewHolder){  
                val paragraphElement = element as ParagraphElement  
            	holder.paragraphText.text= paragraphElement.text  
	    }  
	}  
    }
}
```

Then replace the default adapter with your adapter:
```Kotlin
HtmlRecycler.Builder(this)  
    .setSource(StringSource(Data.data))  
    .setAdapter(BetterImplementationThanTheAuthorsAdapter()) // this is a custom adapter  
    .setRecyclerView(recyclerView)  
    .build()
```

### How to add Data
Data can come from different sources, the library support the following:

 - [x] Assets
 - [x] File
 - [x] String
 - [x] Network (runs on `UI thread` by default so you have to run it on different thread or write your own Source Implementation )

### Write your own source
Simply implement the `Source` interface which will return a `Document` of the parsed `Source`:
```Kotlin
class FileSource(val file: File) : Source {  
    override fun get(): Document {  
        return Jsoup.parse(file, "UTF-8")  
    }  
}
```

## Attach Click listeners on elements
In `DefaultElemetsAdapter` class at line [#27](https://github.com/m7mdra/HtmlRecylcer/blob/master/htmlrecycler/src/main/java/m7mdra/com/htmlrecycler/adapter/DefaultElementsAdapter.kt#L27) l i defined a [higher-order-function](https://kotlinlang.org/docs/reference/lambdas.html#higher-order-functions) in the constructor method (which dose the same as defining an interface) and on line [#75](https://github.com/m7mdra/HtmlRecylcer/blob/master/htmlrecycler/src/main/java/m7mdra/com/htmlrecycler/adapter/DefaultElementsAdapter.kt#L75) we envoke the method passing our element and the position of the clicked view.
## What about Unimplemented elements ?

the library will mark any unimplemented element as `UnknownElement` and will delegate it to the `android.text.Html#fromHtml` class to convert it `Spans` and will be displayed on `TextView`

## TODO list: 
 - [ ] Define a standard Layout styling.
 - [x] allow `NetworkSource` to run on `UI thread` without crashing. 
 - [ ] Support the following elements:
	 - [ ] `Table`  
	 - [x] `Div`
	 - [x] `Section`
	 - [ ] `Superscript` and `Subscrpit`
 - [ ] Test Element Extractors for different data sets.
 - [x] ~~add more control over paragraph element.~~ paragraph element will be rendered using the `android.text.Html` class rather than handled by the library
 - [ ] other thing that i come up with...
 
## Dependencies:
 - [~~FlowLayoutManager~~](https://github.com/xiaofeng-han/AndroidLibs/tree/master/flowlayoutmanager)
 - [Jsoup](https://jsoup.org/)
 - [Picasso](https://github.com/square/picasso)

PR are **welcome** just use crtl+alt+L or (command + alt+L for mac ... idk if right) after every time your finish write code to **format it**.
## projects using this library:
if you are using this library in your project let me by sending an email at xm7mdrax@gmail.com know and i will post it here.

- be the first on the list.


