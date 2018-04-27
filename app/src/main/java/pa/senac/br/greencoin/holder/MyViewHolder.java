package pa.senac.br.greencoin.holder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pa.senac.br.greencoin.R;

public class MyViewHolder extends RecyclerView.ViewHolder
         {

    public TextView mTitulo;
    public TextView mPreco;
    public TextView mPeso;
    public TextView mAnunciante;
    public TextView mData;

    public ImageView mImagem;

    public MyViewHolder(View view) {
        super(view);
        //nome = (TextView) view.findViewById(R.id.item_livro_nome);
        mTitulo = view.findViewById(R.id.lista_titulo);
        mPreco = view.findViewById(R.id.lista_preco);
        mPeso = view.findViewById(R.id.lista_peso);
        mAnunciante = view.findViewById(R.id.lista_anunciante);
        mData = view.findViewById(R.id.lista_data);

        mImagem = view.findViewById(R.id.lista_imagem);

        // restante das buscas
    }



}
