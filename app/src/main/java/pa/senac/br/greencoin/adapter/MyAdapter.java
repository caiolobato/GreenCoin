package pa.senac.br.greencoin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import pa.senac.br.greencoin.model.Anuncio;

import java.util.List;

import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.model.Anuncio;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    //private String[] mDataset;
    private List<Anuncio> mList;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitulo;
        public TextView mPreco;
        public TextView mPeso;
        public TextView mAnunciante;
        public TextView mData;

        public ImageView mImagem;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.lista_titulo);
            mPreco = itemView.findViewById(R.id.lista_preco);
            mPeso = itemView.findViewById(R.id.lista_peso);
            mAnunciante = itemView.findViewById(R.id.lista_anunciante);
            mData = itemView.findViewById(R.id.lista_data);

            mImagem = itemView.findViewById(R.id.lista_imagem);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(List<Anuncio> list) {
//        this.mList = list;
//    }

    public MyAdapter(Context context, List<Anuncio> list) {
        this.mList = list;
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anuncio_bkp, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

        Anuncio anuncio = mList.get(position);

        holder.mTitulo.setText(anuncio.getTitulo());
        holder.mPreco.setText(anuncio.getPreco());
        holder.mPeso.setText(anuncio.getPeso());
        holder.mAnunciante.setText(anuncio.getOwnerName());
        holder.mData.setText(anuncio.getData());

        //Colocar as imagens com picasso? glide? Verificar!!! Por enquanto botei uma qualquer.
        holder.mImagem.setImageResource(R.drawable.ic_launcher_foreground);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
