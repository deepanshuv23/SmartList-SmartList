package cz.uhk.fim.skoreto.todolist.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.uhk.fim.skoreto.todolist.R;
import cz.uhk.fim.skoreto.todolist.model.DataModel;
import cz.uhk.fim.skoreto.todolist.model.Task;
import cz.uhk.fim.skoreto.todolist.model.TaskList;

/**
 * Vlastni ArrayAdapter pro definici aplikacni logiky polozky v listu seznamu ukolu.
 * Created by Tomas.
 */
public class TaskListAdapter extends ArrayAdapter<TaskList> {

    DataModel dm = new DataModel(getContext());

    private class ViewHolder {
        TextView tvTaskListName;
        TextView tvTasksCount;
    }

    public TaskListAdapter(Context context, ArrayList<TaskList> taskLists) {
        super(context, 0, taskLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Ziskej data pro seznam ukolu z teto pozice.
        final TaskList taskList = getItem(position);

        // Over, zda se znovupouziva existujici view, jinak inflatuj toto view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_lists_item, parent, false);

            holder = new ViewHolder();
            holder.tvTaskListName = (TextView) convertView.findViewById(R.id.tvTaskListName);
            holder.tvTasksCount = (TextView) convertView.findViewById(R.id.tvTasksCount);

            // Zjisti pocet nedokoncenych ukolu v seznamu.
            List<Task> tasksByListId = dm.getIncompletedTasksByListId(taskList.getId(), true);
            int tasksCount = tasksByListId.size();
            holder.tvTasksCount.setText("(" + tasksCount + ")");

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Zamezeni preteceni nazvu seznamu ukolu v seznamu seznamu ukolu.
        if (taskList.getName().length() > 40) {
            holder.tvTaskListName.setText(taskList.getName().substring(0, 40) + " ...");
        } else {
            holder.tvTaskListName.setText(taskList.getName());
        }

        return convertView;
    }

}
