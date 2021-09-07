package com.example.demojpa.controllers;

import com.example.demojpa.models.Cliente;
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
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes("cliente") //Se indica el objeto que queremos conserve sus atributos durante la sesion
public class ListarController {

    @Autowired
    private ServiceInterface service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        //Cliente cliente = service.findOne(id);
        Cliente cliente = service.fetchbyIdWithFacturas(id);
        //Para optimizar consulta se usa un query personalizado que utiliza un join para cargar las facturas en una sola consulta
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }


    //Métdodo Get listar Rest
    @GetMapping(value = "/listar-rest")
    public  @ResponseBody List<Cliente> listarRest(){
        return service.findAll();
    }


    //Listar es público, se puede quedar sin secured
    // /listar?page=1&nombre=val1&apellido=val2&email=val3
    @RequestMapping(value = {"/listar","/",},method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page",defaultValue = "0") int page,
                         @RequestParam(name = "nombre",defaultValue = "") String nombre,
                         @RequestParam(name = "apellido", defaultValue = "") String apellido,
                         @RequestParam(name = "email",defaultValue = "") String email,
//                         @RequestParam(name = "val",required = false) String val, //Se agregó este parámetro para intentar hacer busqueda con filtrado
//                         @RequestParam(name = "sortField",defaultValue = "apellido",required = false) String sortField,
//                         @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir,
                         Model model){

//        Pageable pageRequest = PageRequest.of(page,5);

//        Page<Cliente> clientes = service.findAll(pageRequest);
//        Page<Cliente> clientes = service.findClienteByNombre(val,pageRequest);
//        Page<Cliente> clientes = service.findAll(page,sortField,sortDir);

        Page<Cliente> clientes = service.findByCriteria(page,10,nombre,apellido,email);
        PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes,nombre,apellido,email);

        System.out.println(clientes);
        model.addAttribute("titulo","Listado clientes");
        model.addAttribute("clientes",clientes);
//        model.addAttribute("nombre",clientes);
//        model.addAttribute("apellido",pageRender);
//        model.addAttribute("email",clientes);
        model.addAttribute("page",pageRender);
//        model.addAttribute("sortField",sortField);
//        model.addAttribute("sortDir",sortDir);

        return "listar";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/form")
    public String crear(Map<String, Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario cliente");
        return "form";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable (value = "id") Long id, Map<String,Object> model, RedirectAttributes flash){

        Cliente cliente = null;

        if(id > 0) {
            cliente = service.findOne(id);

            if(cliente == null){
                flash.addFlashAttribute("error","El cliente no existe en registro");
                return "redirect:/listar";
            }
        }else{
            flash.addFlashAttribute("error","El id cliente no puede ser cero");
            return "redirect:/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Editar cliente");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){
        //La anotación valid es necesaria para que se hagan las validaciones en objeto Cliente
        //BindingResult debe ir juto con objeto Cliente que es el que se devulve a la vista
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario cliente");
            return "form";
        }

        String mensajeFlash = (cliente.getId()!=null)? "Cliente editado con éxito" : "Cliente creado con éxito";

        service.save(cliente);
        status.setComplete();    //En este momento de destruye objeto cliente despues de persistir
        flash.addFlashAttribute("success",mensajeFlash);
     return "redirect:listar";
    }


    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable (value = "id") Long id,RedirectAttributes flash){

        if(id > 0){
            service.eliminar(id);
            flash.addFlashAttribute("success","Cliente eliminado con éxito");
        }

        return "redirect:/listar";
        }

}

