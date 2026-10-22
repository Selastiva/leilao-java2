(function(){"use strict";
const API="/api/produtos",THEME_KEY="casaLeiloesTemaEtapa9";
function money(v){return Number(v||0).toLocaleString("pt-BR",{style:"currency",currency:"BRL"});}
function escapeHtml(v){return String(v==null?"":v).replace(/[&<>"']/g,c=>({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#039;"}[c]));}
function mapProduct(p){return {id:p.id,name:p.nome,category:p.categoria,description:p.descricao,initialValue:Number(p.valorInicial),currentBid:Number(p.maiorLance||0),status:p.vendido?"Vendido":"Ativo",bids:p.lances||[],emoji:({"Informática":"💻","Eletrônicos":"🎧","Casa":"🪑","Esporte":"🚲","Colecionáveis":"⌚"})[p.categoria]||"◇",createdAt:p.criadoEm};}
async function request(url,options){const r=await fetch(url,{headers:{"Content-Type":"application/json",...(options&&options.headers||{})},...options});let body=null;try{body=await r.json()}catch(e){}if(!r.ok)throw new Error(body&&(body.erro||body.message)||"Não foi possível concluir a operação.");return body;}
async function readProducts(){
 const products=await request(API);
 // A API mantém os lances em um endpoint próprio; carregar cada histórico evita
 // mostrar sempre zero lances nos cartões da página inicial e da listagem.
 return Promise.all(products.map(async p=>{
  let lances=[];
  try{lances=await request(API+"/"+encodeURIComponent(p.id)+"/lances");}catch(e){lances=[];}
  return mapProduct({...p,lances});
 }));
}
function statusClass(s){return s==="Vendido"?"status-sold":"status-active";}function currentValue(p){return Number(p.currentBid)>0?Number(p.currentBid):Number(p.initialValue);}
function productCard(p){return '<article class="product-card"><div class="product-visual visual-blue"><span class="product-emoji">'+escapeHtml(p.emoji||"◇")+'</span><span class="status-badge '+statusClass(p.status)+'">'+escapeHtml(p.status)+'</span></div><div class="product-card-body"><div class="product-category">'+escapeHtml(p.category)+'</div><h3>'+escapeHtml(p.name)+'</h3><p class="product-description">'+escapeHtml(p.description)+'</p><div class="product-price-row"><div><span class="price-label">'+(p.currentBid>0?"Maior lance":"Valor inicial")+'</span><strong class="product-price">'+money(currentValue(p))+'</strong></div><span class="bid-count">↗ '+Number(p.bids.length)+' lance(s)</span></div><a class="button button-secondary button-full" href="detalhes.html?id='+encodeURIComponent(p.id)+'">'+(p.status==="Vendido"?"Ver detalhes":"Ver produto")+' <span>→</span></a></div></article>';}
function notify(msg,type){const t=document.getElementById("toast");if(!t)return;t.textContent=msg;t.className="toast show"+(type==="error"?" toast-error":"");setTimeout(()=>t.className="toast",3200);}
function setFieldError(name,msg){const t=document.querySelector('[data-error="'+name+'"]');if(t)t.textContent=msg||"";}
function clearFieldErrors(form){form.querySelectorAll(".field-error").forEach(x=>x.textContent="");}
function initTheme(){if(localStorage.getItem(THEME_KEY)==="dark")document.body.classList.add("dark-theme");const b=document.getElementById("themeToggle");if(b)b.addEventListener("click",()=>{document.body.classList.toggle("dark-theme");localStorage.setItem(THEME_KEY,document.body.classList.contains("dark-theme")?"dark":"light");});}
document.addEventListener("DOMContentLoaded",()=>{const page=document.body.dataset.page;document.querySelectorAll("[data-nav]").forEach(a=>{if(a.dataset.nav===page)a.classList.add("active")});initTheme();});
window.LeilaoApp={API,request,readProducts,money,escapeHtml,currentValue,statusClass,productCard,notify,setFieldError,clearFieldErrors,mapProduct};})();