package com.mvvm.view;

/**
 * Created by zyfx_ on 2017/5/6.
 * Error:Execution failed for task ':app:dataBindingProcessLayoutsDebug'.
 >
 *
 */
/*public class MainAdapter extends RecyclerView.Adapter<MainAdapter.BindingHolder> {

    private Context context;
    private List<Person> personList;
    private LayoutInflater mInflater;

    public MainAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding itemMainBinding = DataBindingUtil.inflate(mInflater, R.layout.item_main, parent, false);
        return new BindingHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        MainViewModel viewModel = new MainViewModel(personList.get(position));
        holder.mainBinding.setMainModel(viewModel);
}

    @Override
    public int getItemCount() {
        return personList != null && personList.size() > 0 ? personList.size() : 0;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ItemMainBinding mainBinding;

        public BindingHolder(ItemMainBinding mainBinding) {
            super(mainBinding.llContent);
            this.mainBinding = mainBinding;
        }
    }
}*/
