package com.example.demojpa.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class PageRender<T> {

    private String url;
    private Page<T> page;

    private int totalPaginas;
    private int numElementosPorPagina;
    private int paginaActual;
    private List<ItemPage> paginas;

    //Filter values
    private String nombre;
    private String apellido;
    private String email;

    public PageRender(String url, Page<T> page, String nombre, String apellido, String email) {
        //Seteando ls valores de los filtros actuales para retornarlos a la vista
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<>(Collections.EMPTY_LIST);
        this.numElementosPorPagina = page.getSize();
        this.totalPaginas = page.getTotalPages();
        this.paginaActual = page.getNumber() + 1;

        int desde,hasta;
        if(totalPaginas <= numElementosPorPagina/2) {
            desde = 1;
            hasta = totalPaginas;
        }else{
            if(paginaActual <= numElementosPorPagina/2) {
                desde = 1;
                hasta = numElementosPorPagina;
            } else if(paginaActual >= totalPaginas - numElementosPorPagina/2) {
                desde = totalPaginas - numElementosPorPagina + 1;
                hasta = numElementosPorPagina;
            } else {
                desde = paginaActual - numElementosPorPagina/2;
                hasta = numElementosPorPagina;
            }
        }

        for(int i=0; i < hasta; i++){
            paginas.add(new ItemPage(desde + i,paginaActual == desde + i));
        }
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}
