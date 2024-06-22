public class Produto {
    private int id;
    private String nome;
    private double valor;
    private int quantidade;
    private String ean;
    private String tarja;

    public Produto(int id, String nome, double valor, int quantidade, String ean,String tarja) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.ean = ean;
        this.tarja = tarja;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
    public String getEan(){
        return ean;
    }

    public String getTarja() {
        return tarja;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public void setEan(String ean){
        this.ean=ean;
    }
    public void setTarja(String tarja) {
        this.tarja = tarja;
    }

    @Override
    public String toString() {
        return "Produto [id= " + id + ", nome= " + nome + ", valor= " + valor + ", quantidade= " + quantidade + ", Codigo de barras= " + ean+ " tarja= " + tarja + "]";
    }
}





