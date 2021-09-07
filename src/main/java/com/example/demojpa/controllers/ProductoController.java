package com.example.demojpa.controllers;


import com.example.demojpa.models.Cliente;
import com.example.demojpa.models.Producto;
import com.example.demojpa.service.ServiceInterface;
import com.example.demojpa.utils.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class ProductoController {

    @Autowired
    private ServiceInterface service;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/producto/listarProd"},method = RequestMethod.GET)
    public String listarProd(@RequestParam(name = "page",defaultValue = "0") int page, Model model){

        Pageable pageRequest = PageRequest.of(page,10);

        Page<Producto> productos = service.findAllProductos(pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("/llistar",productos,"","","");

        model.addAttribute("titulo","Listado productos");
//        model.addAttribute("productos",service.findAllProductos());
        model.addAttribute("productos",productos);
        model.addAttribute("page",pageRender);

        return "producto/listarProd";
    }

    @RequestMapping(value = "/producto/form")
    public String crearProd(Map<String,Object> model){
        Producto producto = new Producto();
        model.put("producto",producto);
        model.put("titulo","Alta producto");
        return "producto/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/producto/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String,Object> model,RedirectAttributes flash){

        Producto producto = null;

        if(id > 0) {
            producto = service.findProductoById(id);

            if(producto==null){
                flash.addFlashAttribute("error","El cliente no existe en resgistro");
                return "redirect:/listarProd";
            }
        }else{
            flash.addFlashAttribute("error","El id producto no puede ser cero");
            return "redirect:/listarProd";
        }
        model.put("producto",producto);
        model.put("titulo","Editar producto");
        return "producto/form";
    }

    @RequestMapping(value = "/producto/form", method = RequestMethod.POST)
    public String guardarProd(@Valid Producto producto, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo","Alta producto");
            return "producto/form";
        }

        String mensajeFlash = (producto.getId()!=null)? "Producto editado con éxito":"Producto creado con éxito";
        service.saveProducto(producto);
        status.setComplete();
        flash.addFlashAttribute("",mensajeFlash);
        return "redirect:listarProd";
    }

    @RequestMapping("/producto/eliminar/{id}")
    public String eliminar(@PathVariable (value = "id") Long id,RedirectAttributes flash){

        if(id > 0){
            service.eliminarProducto(id);
            flash.addFlashAttribute("success","Producto eliminado con éxito");
        }

        return "redirect:/producto/listarProd";
    }

}
