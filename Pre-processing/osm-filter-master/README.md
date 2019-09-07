# Filtro OSM

O Filtro OSM tem por objetivo eliminar as vias indesejadas dos arquivos .osm, conforme o interesse do usuário. O filtro também é capaz de remover nós intermediários que descrevem a geometria de vias em curva. O arquivo filtrado serve de entrada para o netconvert que gera a rede a ser carregada no SUMO para a simulação.

Caso seja necessária a simplificação do mapa SEM a remoção da geometria, opte pelo outro [filtro OSM](https://wiki.inf.ufrgs.br/OSM_C_Tools_Filter), opção mais rápida.

## Uso do Filtro OSM

O Filtro OSM deve ser usado para remover vias indesejadas após a obtenção de um arquivo no formato .osm. Para uma ajuda na obtenção desse arquivo, consulte as [Dicas_sobre_uso_do_SUMO#OpenStreetMaps](https://wiki.inf.ufrgs.br/Dicas_sobre_uso_do_SUMO#OpenStreetMaps).

Para usar o Filtro OSM, basta seguir a sequência de passos descrita em vermelho na figura abaixo. Mais detalhes sobre cada passo são apresentados adiante.

![alt text](https://github.com/maslab-ufrgs/osm-filter/blob/master/800px-Osmfilter.png)

1. Escolher sistema operacional - Marque o checkbox correspondente ao S.O. que você está usando. Quando se está no Windows, o OSM Filter abre o prompt de comando ao filtrar o arquivo (após o passo 6) para que o NETCONVERT possa ser chamado.

2. Carregar arquivo .osm - selecione o arquivo do OpenStreetMap que deseja filtrar.

3. Selecionar vias que serão mantidas - marque os checkboxes correspondentes aos tipos de vias que serão mantidas. Os tipos são definidos na documentação do [OpenStreetMap](https://wiki.openstreetmap.org/wiki/User:Gyrbo/Road_types).

4. Remover geometria (opcional) - um arquivo OpenStreetMap representa curvas como seções retilíneas conectadas por nós intermediários. Para remover esses nós, marque o checkbox 'Remove Geometry'.

5. Clique em 'Build file' para gerar o arquivo filtrado.

6. Salve o arquivo em um diretório de sua preferência com a extensão .osm (ele ainda precisa ser convertido para o formado do SUMO).7.

7. Use o NETCONVERT para converter o arquivo que foi salvo para o formato do SUMO. Exemplo:

        netconvert --osm [nome-do-arquivo-salvo]

Por padrão, o NETCONVERT gera um arquivo chamado net.net.xml, o qual pode ser simulado no SUMO.

O arquivo do SUMO gerado pelo NETCONVERT pode conter algum defeito (como cruzamentos complexos gerados de forma errada, por exemplo). Caso isso aconteça, consulte a página com dicas para corrigir esses defeitos: [Dicas_sobre_uso_do_SUMO#Correção_de_Defeitos](https://wiki.inf.ufrgs.br/Dicas_sobre_uso_do_SUMO#Corre.C3.A7.C3.A3o_de_Defeitos).

## Notas

Para visualizar o arquivo gerado pelo Filtro OSM antes de usar o NETCONVERT, use o utilitário [JOSM](https://josm.openstreetmap.de/). Ele permite edição de arquivos .osm. Isto pode ser útil para ver se um eventual defeito no mapa é causado pelo NETCONVERT ou pelo processo de filtragem.

Para mais opções de uso do NETCONVERT, consulte a documentação na [página do SUMO](http://sumo.dlr.de/wiki/Simulation_of_Urban_MObility_-_Wiki).

O filtro foi desenvolvido em java, na IDE [Eclipse](http://www.eclipse.org/) com o addin para interface [Window Builder](http://www.eclipse.org/windowbuilder/).

Ao chegar novamente em uma versão estável, crie uma cópia dela com o número da versão no diretório tag do projeto e também um arquivo para download na [página correspondente](https://github.com/maslab-ufrgs/osm-filter/releases).
