package  aspects.funcionalidades;

public aspect PedidoDeAjuda {
	before() : execution(* *.main(..)) {
		// cria uma instancia da funcionalidade
		core.funcionalidades.PedidoAjuda.setInstance();
	}
}