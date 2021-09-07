package com.example.demojpa.controllers;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.models.Factura;
import com.example.demojpa.models.ItemFactura;
import com.example.demojpa.models.Producto;
import com.example.demojpa.service.ServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private ServiceInterface service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
                      Model model,
                      RedirectAttributes flash){

        //Factura factura = service.findFacturaById(id);
        //Para hacer una consulta más eficiente se eprsonaliza el query con join fetch
        Factura factura  = service.fetchFacturaByIdWithClienteWithitemFacturaWithProducto(id);

        if(factura == null){
            flash.addFlashAttribute("error","La factura no existe en base");
            return "redirect:/listar";
        }

        model.addAttribute("factura",factura);
        model.addAttribute("titulo","Factura :".concat(factura.getDescripcion()));

        return "factura/ver";
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value="clienteId") Long clienteId,
                        Map<String, Object> model,
                        RedirectAttributes flash){

        Cliente cliente = service.findOne(clienteId);

        if(cliente == null){
            flash.addAttribute("error","El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura",factura);
        model.put("titulo","Crear factura");

        return "factura/form";
    }


    @GetMapping(value="/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        return service.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required=false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required=false)Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status) {

        if(result.hasErrors()) {
            model.addAttribute("titulo","Crear factura");
            return "factura/form";
        }

        if(itemId == null || itemId.length==0){
            model.addAttribute("titulo","Crear factura");
            model.addAttribute("error","Error: La factura debe tener almenos una linea");
            return "factura/form";
        }

        for(int i = 0; i < itemId.length; i++){

            Producto producto = service.findProductoById(itemId[i]);

            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);

            factura.addItemFactura(linea);

            log.info("Id: "+ itemId[i] + ",cantidad: " + cantidad[i].toString());
        }

        service.saveFactura(factura);
        status.setComplete();

        flash.addFlashAttribute("success","Factura creada con éxito");

        return "redirect:/ver/"+factura.getCliente().getId();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id,
                           RedirectAttributes flash){

        Factura factura = service.findFacturaById(id);

        if(factura != null){
            service.deleteFactura(id);
            flash.addFlashAttribute("success","Se eliminó factura con éxito");
            return "redirect:/ver/"+factura.getCliente().getId();
        }

        flash.addFlashAttribute("error","La factura no existe, no se logro eliminar");
        return "redirect:/listar";
    }

}
