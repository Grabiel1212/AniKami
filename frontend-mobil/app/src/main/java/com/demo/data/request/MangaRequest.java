package com.demo.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MangaRequest {

    private int mangaId;
    private int inicio;
    private int fin;

    public MangaRequest(int mangaId) {
        this.mangaId = mangaId;
    }
}
