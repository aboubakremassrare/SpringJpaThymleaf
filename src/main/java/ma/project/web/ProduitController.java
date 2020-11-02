package ma.project.web;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ma.project.dao.ProduitRepository;
import ma.project.entities.Produit;

@Controller
public class ProduitController {
	@Autowired
	private ProduitRepository produitrepository;
	
	
	@RequestMapping(value="/403",method=RequestMethod.GET)
	public String accessDenied(){
		
		return "accessDenied";
		
	}
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String defaultlogin(){
		
		return "redirect:/user/index";
		
	}
	
	@RequestMapping(value="/user/index")
	public String index(Model model,
			@RequestParam(name="page",defaultValue = "0")int p,
			@RequestParam(name="size",defaultValue = "10")int s,
			@RequestParam(name="motcle",defaultValue = "")String mc){
	
		//Page<Produit> pageProduits = produitrepository.findAll( PageRequest.of(p,s ));
		Page<Produit> pageProduits = produitrepository.chercher('%'+mc+'%',PageRequest.of(p,s));
		model.addAttribute("listeProduits", pageProduits.getContent());
		
		int[] pages=new int[pageProduits.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("size",s);
		model.addAttribute("pageCourante",p);
		model.addAttribute("motcle", mc);
		
		return "produits";
	}
	
	@RequestMapping(value="/admin/delete",method=RequestMethod.GET)
	public String delete(Long id,String motcle,int page,int size){
		
		produitrepository.deleteById(id);
		return "redirect:/user/index?page="+page+"&size="+size+"&motcle="+motcle;
		
	}
	
	@RequestMapping(value="/admin/form",method=RequestMethod.GET)
	public String vueCreate(Model model){
		 
		model.addAttribute("produit", new Produit());
		return "vueCreate";
		
	}
	
	@RequestMapping(value="/admin/save",method=RequestMethod.POST)
	public String save(Model model,@Valid Produit produit,BindingResult Br){
		
		if(Br.hasErrors()) 
			return "vueCreate";
		
		produitrepository.save(produit); 
		return "Confirmation";
		
	}
	
	@RequestMapping(value="/admin/edit",method=RequestMethod.GET)
	public String vueEdit(Model model,Long id){
		
		Optional<Produit> p=produitrepository.findById(id);
		  if(p.isPresent()) {
		  Produit produit = p.get();
		  model.addAttribute("produit",produit);
		  }
		 
		return "vueEdit";
		
	}
	

}
