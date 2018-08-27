package m7mdra.com.htmlrecycler;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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



    private HtmlRecycler(Builder builder) {
        recyclerView = builder.recyclerView;
        adapter = builder.adapter;
        source = builder.source;
    }

    public static final class Builder {
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

        public Builder setAdapter(ElementsAdapter val) {
            adapter = val;
            return this;
        }

        public Builder setSource(Source val) {
            source = val;
            return this;
        }

        public HtmlRecycler build() {
            List<Element> elements = new ArrayList<>();
            ElementIdentifier.extractData(elements, source.get().body().children());
            recyclerView.setAdapter(adapter);
            adapter.addElements(elements);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            return new HtmlRecycler(this);
        }
    }
}
