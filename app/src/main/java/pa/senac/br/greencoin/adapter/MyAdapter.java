package pa.senac.br.greencoin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;


import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import pa.senac.br.greencoin.GlideApp;
import pa.senac.br.greencoin.holder.MyViewHolder;
import pa.senac.br.greencoin.model.Anuncio;

import java.util.List;

import pa.senac.br.greencoin.R;

public class MyAdapter extends RecyclerView.Adapter {

    private List<Anuncio> mList;
    private Context context;
    private MyViewHolder holder;

    private int position;


    public MyAdapter(List<Anuncio> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_anuncio, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
                                 final int position) {

        holder = (MyViewHolder) viewHolder;

        final Anuncio anuncio = mList.get(position);

        // glide quebrou o macete doido

        if (anuncio.getImagemUid()==null) holder.mImagem.setImageResource(R.drawable.nophoto);
        else GlideApp
                .with(context)
                .load(anuncio.getImagemUid())
                .centerCrop()
                .placeholder(new ColorDrawable(Color.BLACK))
                .transition(withCrossFade())
                .into(holder.mImagem);


        holder.mTitulo.setText(anuncio.getTitulo());
        holder.mPreco.setText("Preço: R$ " + anuncio.getPreco());
        holder.mPeso.setText("Peso: " + anuncio.getPeso() + " Kg");
        holder.mAnunciante.setText(anuncio.getOwnerName());
        holder.mData.setText(anuncio.getData());


        //holder.mImagem.setImageResource(R.drawable.nophoto);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.mImagem);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_anuncios);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //Toast.makeText(context, "+++ Informações", Toast.LENGTH_SHORT).show();
                                verContato(anuncio);
                                break;
//                            case R.id.menu2:
//                                //handle menu2 click
//                                break;
//                            case R.id.menu3:
//                                //handle menu3 click
//                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void verContato(Anuncio anuncio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Contato")
                .setMessage("E-mail: " + anuncio.getOwnerName() + "@gmail.com" + "\nTelefone: 982733346") // to passando no hard code, mentindo no e-mail e telefone
                .setIcon(android.R.drawable.ic_menu_info_details)
                .setPositiveButton("Ligar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Uri uri = Uri.parse("tel:982733346"); // to passando no hardcode, falta ajeitar
                        Intent callIntent = new Intent(Intent.ACTION_DIAL,uri);

                        context.startActivity(callIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // só fecha o dialog
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    public String getUrlImage(Anuncio anuncio){
//        String url;
//
//        if(anuncio.getImagemUid()!=null) url = anuncio.getImagemUid();
//        else url = "R.drawable.nophoto";
//
//        return url;
//    }

}
