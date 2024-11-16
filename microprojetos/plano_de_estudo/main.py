import pandas as pd
import re

assuntos = []
materias = []
padrao = r"(^[\d. ]*)"
 
def obter_materias(csv):
    df = pd.read_csv(csv)
    df.columns = ['pt', 'rlm', 'legis', 'TI']
    return df

def percorrer_materias(tabela):
    for materia, topicos in tabela.items():
        globals()[f"{materia}"] = []
        listar_topicos_recurcao(topicos.array, materia)

def listar_topicos_recurcao(topicos, materia, i = 0):
    if len(globals().get(f"{materia}")) >= len(topicos):
        return
    
    if pd.notna(topicos[i]):
          topico = re.sub(padrao, "", topicos[i])
          globals().get(f"{materia}").append(topico)
          listar_topicos_recurcao(topicos, materia, i + 1)
    else:
         listar_topicos_recurcao(topicos, materia)

def intercalar_materias_vs_assuntos(tabela):
     percorrer_materias(tabela)
     for i in range(0, len(tabela)):
          if len(assuntos) >= len(tabela):
               return
          assuntos.append(globals().get("pt")[i])
          materias.append("Português")
          assuntos.append(globals().get("rlm")[i])
          materias.append("Rac. Logic.")
          assuntos.append(globals().get("legis")[i])
          materias.append("Legislação")
          assuntos.append(globals().get("TI")[i])
          materias.append("Tec Inform.")

def dataframe_consolidado(tabela) -> pd.DataFrame:
     intercalar_materias_vs_assuntos(materia)
     consolidado = list(zip(materias,assuntos))
     return pd.DataFrame(consolidado, columns=["Materias","Assuntos"])

materia = obter_materias("./materias.csv")
df_cons = dataframe_consolidado(materia)
df_cons.to_excel(r"plano_estudo.xlsx", sheet_name="Plano de Estudo", index=True)


dia = 1
for i in range(0, len(assuntos)):
     if i % 4 == 0:
          print("\n","DIA:",dia, 40*"-", "\n")
          dia = dia + 1
     print(assuntos[i])