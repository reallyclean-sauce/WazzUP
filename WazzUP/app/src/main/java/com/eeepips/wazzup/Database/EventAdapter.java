package com.eeepips.wazzup.Database;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eeepips.wazzup.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private List<Event> events = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder eventHolder, int position) {
        Event currentEvent = events.get(position);
        eventHolder.eventViewTitle.setText(currentEvent.getTitle());
        eventHolder.eventViewDescription.setText(currentEvent.getDescription());
//        eventHolder.eventViewPriority.setText(String.valueOf(currentEvent.getPriority()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    // Gets which element is being swiped or touched
    public Event getEventAt(int position) {
        return events.get(position);

    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView eventViewTitle;
        private TextView eventViewDescription;
//        private TextView eventViewPriority;


        public EventHolder(View itemView) {
            super(itemView);
            eventViewTitle = itemView.findViewById(R.id.event_view_title);
            eventViewDescription = itemView.findViewById(R.id.event_view_description);
//            eventViewPriority = itemView.findViewById(R.id.event_view_priority);

            // To be able to listen to every item in the list
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gets the position of item clicked
                    int position = getAdapterPosition();

                    // Start executing the event clicked
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(events.get(position));
                    }
                }
            });
        }
    }

    // Provides interface for the process
    // that is executed when the item is clicked
    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    // Catches the click of the item
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
