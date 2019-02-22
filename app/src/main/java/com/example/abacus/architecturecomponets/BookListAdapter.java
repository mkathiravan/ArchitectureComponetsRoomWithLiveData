package com.example.abacus.architecturecomponets;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends ListAdapter<Book,BookListAdapter.BookHolder> {


    private BookListAdapter.onItemClickListener listener;

    protected BookListAdapter(@NonNull DiffUtil.ItemCallback<Book> diffCallback) {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public BookListAdapter.BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_item,viewGroup,false);
        return new BookListAdapter.BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BookHolder bookHolder, int i) {

        Book currentBook = getItem(i);
        bookHolder.textViewTitle.setText(currentBook.getTitle());
        bookHolder.textViewDescription.setText(currentBook.getDescription());
        bookHolder.textViewPriority.setText(String.valueOf(currentBook.getPriority()));

    }


    public Book getBookAt(int position)
    {
        return getItem(position);
    }

    class BookHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public BookHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener
    {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(BookListAdapter.onItemClickListener listener)
    {
        this.listener = listener;
    }

}
