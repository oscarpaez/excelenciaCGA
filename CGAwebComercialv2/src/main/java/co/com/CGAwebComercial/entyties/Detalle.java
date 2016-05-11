package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
public class Detalle implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idDetalle;
	
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaContabilidad;
	
	@Column(length = 25)
	private String elementoPep;
	
	private int numeroFactura;
	
	private int posicionFactura;
	
	@Column(length = 10)
	private String claseFactura;
	
	@Column(length = 2)
	private String tipoDoc;
	
	@Column(length = 2)
	private String porReconocer;
	
	@Column(length = 4)
	private String condicionPago;
	
	
	private int sucursal;
	
	@Column(length = 25)
	private String nombreOficina;
	
	
	private int cliente;
	
	@Column(length = 25)
	private String nombreCliente;
	
	private int codMacrosegmento;
	
	@Column(length = 25)
	private String macrosegmento;
	
	private int codSegmento;
	
	@Column(length = 25)
	private String segmeto;
	
	private int codMicroSegmento;
	
	@Column(length = 25)
	private String microSegmeto;
	
	
	private int pedidoVenta;
	
	private int posicionPedido;
	
	@Column(length = 4)
	private String clasePedido;
	
	private int notaEntrega;
	
	private int centroFabricacion;
	
	
	private int zonaVentas;
	
	@Column(length = 3)
	private String moneda;
	
	
	private int funcionario;
	
	private int cedulaEspecialista;
	
	@Column(length = 50)
	private String nombreEspecialista;
	
	
	private int funcionarioI;
	
	private int cedulaVendedorInterno;
	
	@Column(length = 50)
	private String nombreVendedorInt;
	
	private int codMaterialPedido;
	
	@Column(length = 50)
	private String materialPedido;
	
	
	private int linea;
	
	@Column(length = 30)
	private String descripcionLinea;
	
	private int cuentaMayorCosto;
	
	private int centroPooBeneficio;
	
	private int documentoContable;
	
	private int ordenFabricacion;
	
	@Column(length = 4)
	private String unidadMedidaPedido;
	
	private int pesoPedido;
	
	private int valorFacturaBruto;
	
	private int valorDescuentos;
	
	private int descuentoValorBruto;
	
	private int valorFlete;
	
	private int valorDevoluciones;
	
	private int valorNeto;
	
	private int valorAIU;
	
	private int tipoCambiosPedidos;
	
	private int costoPlanPedido;
	
	private int costoDesviacionOrdenes;
	
	private int costoNotificacion;
	
	private int costoEntrega;
	
	private int costoServicio;
	
	private int costoMaterial;
	
	private int costoSubContrato;
	
	private int costoTotal;
	
	private int utilidadPlan;
	
	@Column(scale = 2)
	private float utilPlan;
	
	private int utilReal;
	
	@Column(scale = 2)
	private float rentaReal;

	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaContabilidad() {
		return fechaContabilidad;
	}

	public void setFechaContabilidad(Date fechaContabilidad) {
		this.fechaContabilidad = fechaContabilidad;
	}

	public String getElementoPep() {
		return elementoPep;
	}

	public void setElementoPep(String elementoPep) {
		this.elementoPep = elementoPep;
	}

	public int getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(int numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public int getPosicionFactura() {
		return posicionFactura;
	}

	public void setPosicionFactura(int posicionFactura) {
		this.posicionFactura = posicionFactura;
	}

	public String getClaseFactura() {
		return claseFactura;
	}

	public void setClaseFactura(String claseFactura) {
		this.claseFactura = claseFactura;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getPorReconocer() {
		return porReconocer;
	}

	public void setPorReconocer(String porReconocer) {
		this.porReconocer = porReconocer;
	}

	public String getCondicionPago() {
		return condicionPago;
	}

	public void setCondicionPago(String condicionPago) {
		this.condicionPago = condicionPago;
	}



	public String getNombreOficina() {
		return nombreOficina;
	}

	public void setNombreOficina(String nombreOficina) {
		this.nombreOficina = nombreOficina;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getCodMacrosegmento() {
		return codMacrosegmento;
	}

	public void setCodMacrosegmento(int codMacrosegmento) {
		this.codMacrosegmento = codMacrosegmento;
	}

	public String getMacrosegmento() {
		return macrosegmento;
	}

	public void setMacrosegmento(String macrosegmento) {
		this.macrosegmento = macrosegmento;
	}

	public int getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(int codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getSegmeto() {
		return segmeto;
	}

	public void setSegmeto(String segmeto) {
		this.segmeto = segmeto;
	}

	public int getCodMicroSegmento() {
		return codMicroSegmento;
	}

	public void setCodMicroSegmento(int codMicroSegmento) {
		this.codMicroSegmento = codMicroSegmento;
	}

	public String getMicroSegmeto() {
		return microSegmeto;
	}

	public void setMicroSegmeto(String microSegmeto) {
		this.microSegmeto = microSegmeto;
	}

	public int getPedidoVenta() {
		return pedidoVenta;
	}

	public void setPedidoVenta(int pedidoVenta) {
		this.pedidoVenta = pedidoVenta;
	}

	public int getPosicionPedido() {
		return posicionPedido;
	}

	public void setPosicionPedido(int posicionPedido) {
		this.posicionPedido = posicionPedido;
	}

	public String getClasePedido() {
		return clasePedido;
	}

	public void setClasePedido(String clasePedido) {
		this.clasePedido = clasePedido;
	}

	public int getNotaEntrega() {
		return notaEntrega;
	}

	public void setNotaEntrega(int notaEntrega) {
		this.notaEntrega = notaEntrega;
	}

	public int getCentroFabricacion() {
		return centroFabricacion;
	}

	public void setCentroFabricacion(int centroFabricacion) {
		this.centroFabricacion = centroFabricacion;
	}

	

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	

	public int getCedulaEspecialista() {
		return cedulaEspecialista;
	}

	public void setCedulaEspecialista(int cedulaEspecialista) {
		this.cedulaEspecialista = cedulaEspecialista;
	}

	public String getNombreEspecialista() {
		return nombreEspecialista;
	}

	public void setNombreEspecialista(String nombreEspecialista) {
		this.nombreEspecialista = nombreEspecialista;
	}

	

	public int getCedulaVendedorInterno() {
		return cedulaVendedorInterno;
	}

	public void setCedulaVendedorInterno(int cedulaVendedorInterno) {
		this.cedulaVendedorInterno = cedulaVendedorInterno;
	}

	public String getNombreVendedorInt() {
		return nombreVendedorInt;
	}

	public void setNombreVendedorInt(String nombreVendedorInt) {
		this.nombreVendedorInt = nombreVendedorInt;
	}

	public int getCodMaterialPedido() {
		return codMaterialPedido;
	}

	public void setCodMaterialPedido(int codMaterialPedido) {
		this.codMaterialPedido = codMaterialPedido;
	}

	public String getMaterialPedido() {
		return materialPedido;
	}

	public void setMaterialPedido(String materialPedido) {
		this.materialPedido = materialPedido;
	}

	public String getDescripcionLinea() {
		return descripcionLinea;
	}

	public void setDescripcionLinea(String descripcionLinea) {
		this.descripcionLinea = descripcionLinea;
	}

	public int getCuentaMayorCosto() {
		return cuentaMayorCosto;
	}

	public void setCuentaMayorCosto(int cuentaMayorCosto) {
		this.cuentaMayorCosto = cuentaMayorCosto;
	}

	public int getCentroPooBeneficio() {
		return centroPooBeneficio;
	}

	public void setCentroPooBeneficio(int centroPooBeneficio) {
		this.centroPooBeneficio = centroPooBeneficio;
	}

	public int getDocumentoContable() {
		return documentoContable;
	}

	public void setDocumentoContable(int documentoContable) {
		this.documentoContable = documentoContable;
	}

	public int getOrdenFabricacion() {
		return ordenFabricacion;
	}

	public void setOrdenFabricacion(int ordenFabricacion) {
		this.ordenFabricacion = ordenFabricacion;
	}

	public String getUnidadMedidaPedido() {
		return unidadMedidaPedido;
	}

	public void setUnidadMedidaPedido(String unidadMedidaPedido) {
		this.unidadMedidaPedido = unidadMedidaPedido;
	}

	

	public int getPesoPedido() {
		return pesoPedido;
	}

	public void setPesoPedido(int pesoPedido) {
		this.pesoPedido = pesoPedido;
	}

	public int getValorFacturaBruto() {
		return valorFacturaBruto;
	}

	public void setValorFacturaBruto(int valorFacturaBruto) {
		this.valorFacturaBruto = valorFacturaBruto;
	}

	public int getValorDescuentos() {
		return valorDescuentos;
	}

	public void setValorDescuentos(int valorDescuentos) {
		this.valorDescuentos = valorDescuentos;
	}

	public int getDescuentoValorBruto() {
		return descuentoValorBruto;
	}

	public void setDescuentoValorBruto(int descuentoValorBruto) {
		this.descuentoValorBruto = descuentoValorBruto;
	}

	public int getValorFlete() {
		return valorFlete;
	}

	public void setValorFlete(int valorFlete) {
		this.valorFlete = valorFlete;
	}

	public int getValorDevoluciones() {
		return valorDevoluciones;
	}

	public void setValorDevoluciones(int valorDevoluciones) {
		this.valorDevoluciones = valorDevoluciones;
	}

	public int getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(int valorNeto) {
		this.valorNeto = valorNeto;
	}

	public int getValorAIU() {
		return valorAIU;
	}

	public void setValorAIU(int valorAIU) {
		this.valorAIU = valorAIU;
	}

	public int getTipoCambiosPedidos() {
		return tipoCambiosPedidos;
	}

	public void setTipoCambiosPedidos(int tipoCambiosPedidos) {
		this.tipoCambiosPedidos = tipoCambiosPedidos;
	}

	public int getCostoPlanPedido() {
		return costoPlanPedido;
	}

	public void setCostoPlanPedido(int costoPlanPedido) {
		this.costoPlanPedido = costoPlanPedido;
	}

	public int getCostoDesviacionOrdenes() {
		return costoDesviacionOrdenes;
	}

	public void setCostoDesviacionOrdenes(int costoDesviacionOrdenes) {
		this.costoDesviacionOrdenes = costoDesviacionOrdenes;
	}

	public int getCostoNotificacion() {
		return costoNotificacion;
	}

	public void setCostoNotificacion(int costoNotificacion) {
		this.costoNotificacion = costoNotificacion;
	}

	public int getCostoEntrega() {
		return costoEntrega;
	}

	public void setCostoEntrega(int costoEntrega) {
		this.costoEntrega = costoEntrega;
	}

	public int getCostoServicio() {
		return costoServicio;
	}

	public void setCostoServicio(int costoServicio) {
		this.costoServicio = costoServicio;
	}

	public int getCostoMaterial() {
		return costoMaterial;
	}

	public void setCostoMaterial(int costoMaterial) {
		this.costoMaterial = costoMaterial;
	}

	public int getCostoSubContrato() {
		return costoSubContrato;
	}

	public void setCostoSubContrato(int costoSubContrato) {
		this.costoSubContrato = costoSubContrato;
	}

	public int getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(int costoTotal) {
		this.costoTotal = costoTotal;
	}

	public int getUtilidadPlan() {
		return utilidadPlan;
	}

	public void setUtilidadPlan(int utilidadPlan) {
		this.utilidadPlan = utilidadPlan;
	}

	public float getUtilPlan() {
		return utilPlan;
	}

	public void setUtilPlan(float utilPlan) {
		this.utilPlan = utilPlan;
	}

	public int getUtilReal() {
		return utilReal;
	}

	public void setUtilReal(int utilReal) {
		this.utilReal = utilReal;
	}

	public float getRentaReal() {
		return rentaReal;
	}

	public void setRentaReal(float rentaReal) {
		this.rentaReal = rentaReal;
	}

	public int getSucursal() {
		return sucursal;
	}

	public void setSucursal(int sucursal) {
		this.sucursal = sucursal;
	}

	public int getZonaVentas() {
		return zonaVentas;
	}

	public void setZonaVentas(int zonaVentas) {
		this.zonaVentas = zonaVentas;
	}

	public int getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(int funcionario) {
		this.funcionario = funcionario;
	}

	public int getFuncionarioI() {
		return funcionarioI;
	}

	public void setFuncionarioI(int funcionarioI) {
		this.funcionarioI = funcionarioI;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	
}
