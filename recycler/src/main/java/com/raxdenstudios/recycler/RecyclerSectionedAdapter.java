package com.raxdenstudios.recycler;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by agomez on 27/11/2015.
 */
public abstract class RecyclerSectionedAdapter<O, VSH extends RecyclerView.ViewHolder, T, VIH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ViewType {SECTION, ITEM}

    protected final Context mContext;
    protected Map<O, List<T>> mData;
    protected Map<Integer, O> mSectionPositions;
    protected Map<Integer, T> mItemPositions;

    public RecyclerSectionedAdapter(Context context) {
        this(context, null);
    }

    public RecyclerSectionedAdapter(Context context, Map<O, List<T>> data) {
        mContext = context;
        initData(data);
    }

    private void initData(Map<O, List<T>> data) {
        mData = data != null ? data : new LinkedHashMap<O, List<T>>();
        mSectionPositions = new HashMap<Integer, O>();
        mItemPositions = new HashMap<Integer, T>();

        int sectionPosition = 0;
        for (Map.Entry<O, List<T>> entry : mData.entrySet()) {
            O section = entry.getKey();
            mSectionPositions.put(sectionPosition, section);
            int itemPosition = sectionPosition;
            for (T item : entry.getValue()) {
                itemPosition++;
                mItemPositions.put(itemPosition, item);
            }
            sectionPosition = itemPosition + 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.SECTION.ordinal()) {
            return onCreateViewSectionHolder(parent);
        } else if (viewType == ViewType.ITEM.ordinal()) {
            return onCreateViewItemHolder(parent);
        }
        return null;
    }

    public abstract VSH onCreateViewSectionHolder(ViewGroup parent);

    public abstract VIH onCreateViewItemHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (isSection(position)) {
                onBindViewSectionHolder((VSH) holder, getSectionByAdapterPosition(position), position);
            } else {
                onBindViewItemHolder((VIH) holder, getSectionByAdapterPosition(position), mItemPositions.get(position), position);
            }
        }
    }

    public abstract void onBindViewSectionHolder(VSH holder, O section, int position);

    public abstract void onBindViewItemHolder(VIH holder, O section, T item, int position);

    @Override
    public final int getItemViewType(int position) {
        return isSection(position) ? ViewType.SECTION.ordinal() : ViewType.ITEM.ordinal();
    }

    /**
     * Return the view type of the section at <code>position</code> for the purposes
     * of view recycling.
     *
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     *                 <code>position</code>. Type codes need not be contiguous.
     */
    public int getSectionViewType(int position) {
        return 0;
    }

    /**
     * Return the view type of the sectionItem at <code>position</code> for the purposes
     * of view recycling.
     *
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     *                 <code>position</code>. Type codes need not be contiguous.
     */
    public int getSectionItemViewType(int position) {
        return 0;
    }

    /**
     * Este método nos indica si el item del adaptador es una sección o un elemento de sección a través de la posición enviada por parámetro.
     * @param position
     * @return true si la posición ocupada es una sección.
     */
    public boolean isSection(int position) {
        return mSectionPositions.containsKey(position);
    }

    /**
     * Este método nos indica si el item del adaptador es un item a través de la posición enviada por parámetro.
     * @param position
     * @return true si la posición ocupada es un item.
     */
    public boolean isItem(int position) {
        return mItemPositions.containsKey(position);
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
     * Este método retorna la posición que ocupa la sección en el adapter.
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
            return getSectionPosition(section) + mData.get(section).indexOf(data) + 1;
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

    public O getSectionByAdapterPosition(int position) {
        O section = null;
        if (isSection(position)) {
            section = mSectionPositions.get(position);
        } else {
            for (Map.Entry<Integer, O> entry : mSectionPositions.entrySet()) {
                O possibleSection = entry.getValue();
                int range[] = getRangePosition(possibleSection);
                if (position >= range[0] && position <= range[1]) {
                    section = possibleSection;
                    break;
                }
            }
        }
        return section;
    }

    public O getSection(int sectionPosition) {
        if (mData.keySet().size() >= sectionPosition) {
            return (O) mData.keySet().toArray()[sectionPosition];
        }
        return null;
    }

    public T getItemByAdapterPosition(int position) {
        if (isItem(position)) {
            return mItemPositions.get(position);
        }
        return null;
    }

    public T getItem(int sectionPosition, int sectionItemPosition) {
        O section = getSection(sectionPosition);
        if (section != null) {
            return getItem(section, sectionItemPosition);
        }
        return null;
    }

    public T getItem(O section, int sectionItemPosition) {
        return mData.get(section).get(sectionItemPosition);
    }

    public List<T> getItems(int sectionPosition) {
        O section = getSection(sectionPosition);
        if (section != null) {
            return getItems(section);
        }
        return null;
    }

    public List<T> getItems(O section) {
        return mData.get(section);
    }

    public Map<O, List<T>> getItems() {
        return mData;
    }

    public void setItems(Map<O, List<T>> data) {
        initData(data);
        notifyDataSetChanged();
    }

    /**
     * Añade una sección sin elementos asociados. Una vez creada notifica al adapter de la inserción.
     * @param section
     * @return true si la sección es creada correctamente.
     */
    public boolean addSection(O section) {
        if (!containsSection(section)) {
            mData.put(section, new ArrayList<T>());
            int sectionPosition = getSectionPosition(section);
            mSectionPositions.put(sectionPosition, section);
            notifyItemInserted(sectionPosition);
            return true;
        }
        return false;
    }

    /**
     * Añade una sección con un listado de elementos asociados. Una vez creada notifica al adapter de la inserción.
     * @param section
     * @param data
     * @return true si la sección es creada correctamente.
     */
    public boolean addSection(O section, List<T> data) {
        if (!containsSection(section)) {
            mData.put(section, data);
            int sectionPosition = getSectionPosition(section);
            mSectionPositions.put(sectionPosition, section);
            int itemPosition = sectionPosition;
            for (T item : data) {
                itemPosition++;
                mItemPositions.put(itemPosition, item);
            }
            notifyItemRangeInserted(sectionPosition, itemPosition);
            return true;
        }
        return false;
    }

    /**
     * Añade un item a la sección del listado del adapter. Una vez añadido notifica al adapter de la inserción.
     * @param section
     * @param data
     * @return true si la sección es creada correctamente.
     */
    public boolean addItem(O section, T data) {
        if (containsSection(section)) {
            mData.get(section).add(data);
            int itemPosition = getItemPosition(section, data);
            mItemPositions.put(itemPosition, data);
            notifyItemInserted(itemPosition);
            return true;
        }
        return false;
    }

    /**
     * Añade un item a la sección del listado del adapter en una posición concreta. Una vez añadido notifica al adapter de la inserción.
     * Si la sección existe, añade únicamente el elemento.
     * @param section
     * @param data
     * @param position
     * @return true si el elemento es añadido correctamente
     */
    public boolean addItem(O section, T data, int position) {
        if (containsSection(section)) {
            mData.get(section).add(position, data);
            int itemPosition = getItemPosition(section, data);
            mItemPositions.put(itemPosition, data);
            notifyItemInserted(itemPosition);
            return true;
        }
        return false;
    }

    /**
     * Este método añade una sección con un elemento al adapter además de notificarlo.
     * Si la sección existe, añade únicamente el elemento.
     * @param section
     * @param data
     * @return true si los elementos son añadidos correctamente
     */
    public boolean addItems(O section, List<T> data) {
        if (containsSection(section)) {
            int start = getSectionPosition(section) + mData.get(section).size();
            int itemPosition = start;
            mData.get(section).addAll(data);
            for (T item : data) {
                itemPosition++;
                mItemPositions.put(itemPosition, item);
            }
            int end = start + data.size();
            notifyItemRangeInserted(start, end);
            return true;
        }
        return false;
    }

    public void updateItem(T data) {
        for (Map.Entry<Integer, T> entry : mItemPositions.entrySet()) {
            if (entry.getValue().equals(data)) {
                notifyItemChanged(entry.getKey());
            }
        }
    }

    public void updateItem(O section, T data) {
        if (containsItem(section, data)) {
            notifyItemChanged(getItemPosition(section, data));
        }
    }

    public void updateItem(O section, int position) {
        if (containsSection(section) && mData.get(section).size() > position) {
            T item = mData.get(section).get(position);
            if (item != null) {
                notifyItemChanged(getSectionPosition(section) + position);
            }
        }
    }

    public boolean removeSection(O section) {
        if (containsSection(section)) {
            int sectionPosition = getSectionPosition(section);
            int sizeData = mData.get(section).size();
            mData.get(section).clear();
            mSectionPositions.remove(sectionPosition);
            notifyItemRangeRemoved(sectionPosition, sizeData);
            return true;
        }
        return false;
    }

    public boolean removeItem(O section, T data){
        if (containsItem(section, data)) {
            int itemPosition = getItemPosition(section, data);
            mData.get(section).remove(data);
            notifyItemRemoved(itemPosition);
            return true;
        }
        return false;
    }

    public boolean removeItem(O section, int position){
        if (containsSection(section) && mData.get(section).size() > position) {
            T item = mData.get(section).get(position);
            if (item != null) {
                mData.get(section).remove(item);
                notifyItemRemoved(getSectionPosition(section) + position);
                return true;
            }
        }
        return false;
    }

    public boolean removeItems(O section) {
        if (containsSection(section)) {
            int sectionPosition = getSectionPosition(section);
            int sizeData = mData.get(section).size();
            mData.get(section).clear();
            notifyItemRangeRemoved(sectionPosition + 1 , sizeData);
        }
        return false;
    }

    public void removeItems() {
        mData.clear();
        mSectionPositions.clear();
        mItemPositions.clear();
        notifyDataSetChanged();
    }

}
