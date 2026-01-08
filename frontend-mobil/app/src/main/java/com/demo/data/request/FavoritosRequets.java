package com.demo.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritosRequets {

  private int  usuarioId;
  private int mangaId;

  public FavoritosRequets(int usuarioId){
      this.usuarioId = usuarioId;
  }

}
