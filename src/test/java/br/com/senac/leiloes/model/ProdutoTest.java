package br.com.senac.leiloes.model;
import java.math.BigDecimal;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class ProdutoTest {
 @Test void aceitaLanceMaior(){Produto p=new Produto("Notebook","Informática","Produto em bom estado para teste",new BigDecimal("500"));p.registrarLance(new BigDecimal("600"));assertEquals(0,new BigDecimal("600").compareTo(p.getMaiorLance()));}
 @Test void rejeitaLanceAbaixoDoInicial(){Produto p=new Produto("Notebook","Informática","Produto em bom estado para teste",new BigDecimal("500"));assertThrows(IllegalArgumentException.class,()->p.registrarLance(new BigDecimal("400")));}
 @Test void rejeitaLanceQueNaoSuperaMaior(){Produto p=new Produto("Notebook","Informática","Produto em bom estado para teste",new BigDecimal("500"));p.registrarLance(new BigDecimal("600"));assertThrows(IllegalArgumentException.class,()->p.registrarLance(new BigDecimal("600")));}
 @Test void impedeVendaSemLance(){Produto p=new Produto("Notebook","Informática","Produto em bom estado para teste",new BigDecimal("500"));assertThrows(IllegalStateException.class,p::marcarComoVendido);}
 @Test void impedeLanceDepoisDaVenda(){Produto p=new Produto("Notebook","Informática","Produto em bom estado para teste",new BigDecimal("500"));p.registrarLance(new BigDecimal("600"));p.marcarComoVendido();assertThrows(IllegalStateException.class,()->p.registrarLance(new BigDecimal("700")));}
}
