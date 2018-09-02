package m7mdra.com.htmlrecycler;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import m7mdra.com.htmlrecycler.adapter.ElementsAdapter;
import m7mdra.com.htmlrecycler.elements.Element;
import m7mdra.com.htmlrecycler.elements.ElementIdentifier;
import m7mdra.com.htmlrecycler.source.Source;

public class HtmlRecycler {
    private RecyclerView recyclerView;
    private ElementsAdapter adapter;
    private Source source;
    private LoadCallback mLoadCallback;

    private HtmlRecycler(Builder builder) {
        recyclerView = builder.recyclerView;
        adapter = builder.adapter;
        source = builder.source;
        mLoadCallback = builder.loadingCallback;
    }

    public static final class Builder {
        public LoadCallback loadingCallback;
        private RecyclerView recyclerView;
        private ElementsAdapter adapter;
        private Source source;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setRecyclerView(RecyclerView val) {
            recyclerView = val;
            return this;
        }

        public Builder setLoadingCallback(LoadCallback loadingCallback) {
            this.loadingCallback = loadingCallback;
            return this;
        }

        public Builder setAdapter(ElementsAdapter val) {
            adapter = val;
            return this;
        }

        public Builder setSource(Source val) {
            source = val;
            return this;
        }

        private static final String TAG = "Builder";

        public HtmlRecycler build() {
            final DocumentParseTask documentParseTask = new DocumentParseTask(source, new LoadCallback() {
                @Override
                public void onLoaded(Document document) {
                    if (loadingCallback != null)
                        loadingCallback.onLoaded(document);
                    List<Element> elements = new ArrayList<>();
                    ElementIdentifier.extractData(elements, document.body().children());
                    adapter.addElements(elements);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                }

                @Override
                public void onLoadingStart() {
                    if (loadingCallback != null)
                        loadingCallback.onLoadingStart();
                }

            });
            documentParseTask.execute();
            return new HtmlRecycler(this);
        }
    }

    static class DocumentParseTask extends AsyncTask<Void, Void, Document> {
        private LoadCallback mLoadCallback;
        private Source source;

        public DocumentParseTask(Source source, LoadCallback loadCallback) {
            mLoadCallback = loadCallback;
            this.source = source;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadCallback.onLoadingStart();
        }

        @Override
        protected Document doInBackground(Void... voids) {
            return source.get();
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            mLoadCallback.onLoaded(document);
        }


    }

    public interface LoadCallback {
        void onLoaded(Document document);

        void onLoadingStart();


    }
}
