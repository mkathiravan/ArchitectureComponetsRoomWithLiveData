package com.example.abacus.architecturecomponets;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {


    private List<Book> books = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_item,viewGroup,false);
        return new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int i) {

        Book currentBook = books.get(i);
        bookHolder.textViewTitle.setText(currentBook.getTitle());
        bookHolder.textViewDescription.setText(currentBook.getDescription());
        bookHolder.textViewPriority.setText(String.valueOf(currentBook.getPriority()));

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
        notifyDataSetChanged();
    }

    public Book getBookAt(int position)
    {
        return books.get(position);
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
                        listener.onItemClick(books.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener
    {
       void onItemClick(Book book);
    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
       this.listener = listener;
    }
}
