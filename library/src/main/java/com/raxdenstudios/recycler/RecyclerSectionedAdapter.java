package com.raxdenstudios.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agomez on 27/11/2015.
 */
public abstract class RecyclerSectionedAdapter<O, T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Context mContext;
    protected int mResource;
    protected Map<O, List<T>> mData;

    public RecyclerSectionedAdapter(Context context, int resource) {
        this(context, resource, null);
    }

    public RecyclerSectionedAdapter(Context context, int resource, Map<O, List<T>> data) {
        mContext = context;
        mResource = resource;
        mData = data != null ? data : new LinkedHashMap<O, List<T>>();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    /**
     * Este método nos indica si el item del adaptador es una sección o un elemento de sección a través de la posición enviada por parámetro.
     * @param position
     * @return true si la posición ocupada es una sección.
     */
    public boolean isSection(int position) {
        if (position == 0) return false;
        return true;
    }

    /**
     * Este método nos indica el rango de posiciones que ocupa la sección en el adaptador.
     * @param section
     * @return int[0] posición de inicio de la sección, int[1] posición final de la sección.
     */
    public int[] getRangePosition(O section) {
        int[] range = {0,0};
        if (containsSection(section)) {
            range[0] = getSectionPosition(section);
            range[1] = range[0] + mData.get(section).size();
        }
        return range;
    }

    /**
     * Este método retorna el número de secciones que contiene el adapter.
     * @return
     */
    public int getSectionCount() {
        return mData.keySet().size();
    }

    /**
     * Este método retorna el número de elementos que contiene el adapter.
     * @return
     */
    @Override
    public int getItemCount() {
        int items = 0;
        for (Map.Entry<O, List<T>> entry : mData.entrySet()) {
            items += entry.getValue().size() + 1;
        }
        return items;
    }

    /**
     * Este método retorna el número de elementos que contiene la sección dentro del adapter
     * @param sectionPosition
     * @return
     */
    public int getItemCount(int sectionPosition) {
        return getItemCount(getSection(sectionPosition));
    }

    /**
     * Este método retorna el número de elementos que contiene la sección dentro del adapter
     * @param section
     * @return
     */
    public int getItemCount(O section) {
        return mData.get(section).size();
    }

    /**
     * Este método retona la posición que ocupa la sección en el adapter.
     * @param section
     * @return
     */
    public int getSectionPosition(O section) {
        int position = 0;
        if (containsSection(section)) {
            for (Map.Entry<O, List<T>> entry : mData.entrySet()) {
                if (entry.getKey() == section) {
                    return position;
                } else {
                    position += entry.getValue().size() + 1;
                }
            }
        }
        return -1;
    }

    /**
     * Este método retorna la posición que ocupa un elemento de sección en el adapter.
     * @param section
     * @param data
     * @return
     */
    public int getItemPosition(O section, T data) {
        if (containsSection(section)) {
            return getSectionPosition(section) + mData.get(section).indexOf(data);
        }
        return -1;
    }

    /**
     * Este método retorna la posición que ocupa un elemento de sección en el adapter.
     * @param data
     * @return
     */
    public int getItemPosition(T data) {
        for (Map.Entry<O, List<T>> entry : mData.entrySet()) {
            if (containsItem(entry.getKey(), data)) {
                return getItemPosition(entry.getKey(), data);
            }
        }
        return -1;
    }

    /**
     * Este método nos dice si la sección existe en el adapter.
     * @param section
     * @return
     */
    public boolean containsSection(O section) {
        return mData.containsKey(section);
    }

    /**
     * Este método nos dice si el item existe en el adapter
     * @param data
     * @return
     */
    public boolean containsItem(T data) {
        for (Map.Entry<O, List<T>> entry : mData.entrySet()) {
            if (containsItem(entry.getKey(), data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este método nos dice si el item existe en el adapter
     * @param section
     * @param data
     * @return
     */
    public boolean containsItem(O section, T data) {
        if (containsSection(section)) {
            return mData.get(section).contains(data);
        }
        return false;
    }

    /**
     * Este método añade una sección con un listado de elementos al adapter además de notificarlo.
     * Si la sección existe, añade únicamente los elementos.
     * @param section
     * @param data
     */
    public void addSection(O section, List<T> data) {
        int start; int end;
        if (containsSection(section)) {
            start = getSectionPosition(section) + mData.get(section).size();
            mData.get(section).addAll(data);
        } else {
            start = getSectionPosition(section);
            mData.put(section, data);
        }
        end = start + data.size();
        notifyItemRangeInserted(start, end);
    }

    /**
     * Este método añade una sección con un elemento al adapter además de notificarlo.
     * Si la sección existe, añade únicamente el elemento.
     * @param section
     * @param data
     */
    public void addItem(O section, T data) {
        if (containsSection(section)) {
            mData.get(section).add(data);
        } else {
            mData.put(section, new ArrayList<>(Arrays.asList(data)));
        }
        notifyItemInserted(getItemPosition(section, data));
    }

    /**
     * Este método añade una sección con un elemento al adapter además de notificarlo.
     * Si la sección existe, añade únicamente el elemento.
     * @param section
     * @param data
     */
    public void addItem(O section, T data, int position) {
        if (containsSection(section)) {
            mData.get(section).add(position, data);
        } else {
            mData.put(section, new ArrayList<>(Arrays.asList(data)));
        }
        notifyItemInserted(getItemPosition(section, data));
    }

    /**
     * Este método añade una sección con un elemento al adapter además de notificarlo.
     * Si la sección existe, añade únicamente el elemento.
     * @param section
     * @param data
     */
    public void addItems(O section, List<T> data) {
        int start; int end;
        if (containsSection(section)) {
            start = getSectionPosition(section) + mData.get(section).size();
            mData.get(section).addAll(data);
        } else {
            start = getSectionPosition(section);
            mData.put(section, data);
        }
        end = start + data.size();
        notifyItemRangeInserted(start, end);
    }

//    public int getSectionPosition(O section) {
//        return (new ArrayList<O>(mData.keySet())).indexOf(section);
//    }
//
//    public int getSectionItemPosition(int sectionPosition, T data) {
//        O section = getSection(sectionPosition);
//        if (section != null) {
//            int itemPosition = mData.get(section).indexOf(data);
//            if (itemPosition != -1) {
//                return itemPosition + sectionPosition;
//            }
//        }
//        return -1;
//    }
//
//    public int getSectionItemPosition(O section, T data) {
//        int sectionPosition = getSectionPosition(section);
//        if (sectionPosition != -1) {
//            return getSectionItemPosition(sectionPosition, data);
//        }
//        return -1;
//    }
//
//    public int getSectionItemPosition(T data) {
//        int sectionPosition = 0;
//        for (Map.Entry<O, List<T>> entry : mData.entrySet()) {
//            sectionPosition++;
//            int position = entry.getValue().indexOf(data);
//            if (position != -1) {
//                return position + sectionPosition;
//            }
//        }
//        return -1;
//    }





    private O getSection(int sectionPosition) {
        if (mData.keySet().size() >= sectionPosition) {
            return (O) mData.keySet().toArray()[sectionPosition];
        }
        return null;
    }

    private T getSectionItem(int sectionPosition, int sectionItemPosition) {
        O section = getSection(sectionPosition);
        if (section != null) {
            return getSectionItem(section, sectionItemPosition);
        }
        return null;
    }

    private T getSectionItem(O section, int sectionItemPosition) {
        return mData.get(section).get(sectionItemPosition);
    }







    public void removeItem(O section, T data){
        int sectionPosition = getSectionPosition(section);
        int itemPosition = getSectionItemPosition(sectionPosition, data);
        if (mData.get(sectionPosition).remove(data)) {
            notifyItemRemoved(itemPosition);
        }
    }

    public void removeItem(O section, int position){
        int sectionPosition = getSectionPosition(section);
        if (mData.get(sectionPosition).size() > position) {
            mData.get(sectionPosition).remove(position);
            notifyItemRemoved(sectionPosition + position);
        }
    }

    public void clearItems(O section) {
        int sectionPosition = getSectionPosition(section);
        if (sectionPosition != -1) {
            int sizeData = mData.get(section).size();
            mData.get(section).clear();
            notifyItemRangeRemoved(sectionPosition, sizeData);
        }
    }

    public void clearItems() {
        mData = new LinkedHashMap<O, List<T>>();
        notifyDataSetChanged();
    }

}
