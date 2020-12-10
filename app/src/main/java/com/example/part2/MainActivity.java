package com.example.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.ItemTouchHelper;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.android.material.snackbar.Snackbar;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView itemsContainerRV;
    RecyclerViewAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> data = new ArrayList();
        for(int i = 1; i <= 20; i++) {
            data.add("Item " + i);
        }

        itemsContainerRV = findViewById(R.id.itemsContainerRV);
        itemAdapter = new RecyclerViewAdapter(this, data);
        itemAdapter.notifyDataSetChanged();
        itemsContainerRV.setAdapter(itemAdapter);

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final String item = itemAdapter.getData().get(position);

                itemAdapter.removeItem(position);

                Snackbar snackbar = Snackbar.make(itemsContainerRV, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemAdapter.restoreItem(item, position);
                        itemsContainerRV.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        });
        itemTouchhelper.attachToRecyclerView(itemsContainerRV);
    }
}