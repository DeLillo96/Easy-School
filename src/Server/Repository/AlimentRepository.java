package Server.Repository;

import Server.Entity.Alimento;

import java.util.HashMap;
import java.util.List;

public class AlimentRepository extends AbstractRepository{
    public AlimentRepository() { super("Alimento"); }

    public Alimento getAlimentById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List aliment = read(params);

        return aliment!= null && aliment.size() == 1 ? (Alimento) aliment.get(0) : null;
    }

    public Alimento getAlimentByNome(String nome) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("nome", nome);
        List aliment = read(params);

        return aliment!= null && aliment.size() == 1 ? (Alimento) aliment.get(0) : null;
    }
}
