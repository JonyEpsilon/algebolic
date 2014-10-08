;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit ctrl+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns spacial-storm
  (:require [gorilla-plot.core :as plot]
            [algebolic.expression.core :as expression]
            [algebolic.expression.algebra :as algebra]
            [algebolic.expression.differentiation :as differentiation]
            [algebolic.expression.evaluate :as evaluate]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(differentiation/d 'x [:plus 'x 3])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"(1 0)"}],"value":"[:plus (1 0)]"}
;; <=

;; @@
(algebra/expand-full (differentiation/d 'x [:times [:times 'x 'x] 'x]))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times 1 x]"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times [:times 1 x] x]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times 1 x]"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times [:times 1 x] x]"}],"value":"[:plus [:times [:times 1 x] x] [:times [:times 1 x] x]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times 1 x]"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times [:times 1 x] x]"}],"value":"[:plus [:plus [:times [:times 1 x] x] [:times [:times 1 x] x]] [:times [:times 1 x] x]]"}
;; <=

;; @@
(differentiation/d 'x [:sin [:times 'x 'x]])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times 1 x]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[:times x 1]"}],"value":"[:plus [:times 1 x] [:times x 1]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:cos</span>","value":":cos"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times x x]"}],"value":"[:cos [:times x x]]"}],"value":"[:times [:plus [:times 1 x] [:times x 1]] [:cos [:times x x]]]"}
;; <=

;; @@
(def exx '[:plus [:minus [:plus [:plus [:plus [:cos [:cos [:cos 0.33146952029252286]]] [:sin [:times 0.43364235787892874 x]]] [:cos [:times 0.17868379408844937 x]]] [:cos [:times 0.18167484845112636 x]]] [:cos [:times 0.5878030800263497 x]]] [:sin [:times 0.5304475584893972 x]]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;spacial-storm/exx</span>","value":"#'spacial-storm/exx"}
;; <=

;; @@
(plot/compose
  (plot/plot (evaluate/functionalise exx '[x]) [0 40])
  (plot/plot (evaluate/functionalise (differentiation/d 'x exx) '[x]) [0 40] :colour "blue"))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"626051fa-1747-428e-9f90-243461179615","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"626051fa-1747-428e-9f90-243461179615","field":"data.y"}}],"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"data":[{"name":"626051fa-1747-428e-9f90-243461179615","values":[{"x":0,"y":1.833554815635499},{"x":0.4000000059604645,"y":2.2390552702249624},{"x":0.800000011920929,"y":2.6730865933328976},{"x":1.2000000178813934,"y":3.1172248895352364},{"x":1.600000023841858,"y":3.5515347589227324},{"x":2.0000000298023224,"y":3.955471577860927},{"x":2.400000035762787,"y":4.308843300780938},{"x":2.8000000417232513,"y":4.592785208666024},{"x":3.200000047683716,"y":4.790699182617093},{"x":3.6000000536441803,"y":4.8891097028682395},{"x":4.000000059604645,"y":4.878391882141367},{"x":4.400000065565109,"y":4.75333232316776},{"x":4.800000071525574,"y":4.513491202072583},{"x":5.200000077486038,"y":4.163343364119166},{"x":5.600000083446503,"y":3.7121869188109953},{"x":6.000000089406967,"y":3.1738193038898252},{"x":6.400000095367432,"y":2.5659924694521297},{"x":6.800000101327896,"y":1.9096701122659172},{"x":7.200000107288361,"y":1.2281201769258034},{"x":7.600000113248825,"y":0.5458845890176197},{"x":8.00000011920929,"y":-0.11232507666541858},{"x":8.400000125169754,"y":-0.722752936395855},{"x":8.800000131130219,"y":-1.2636903749802397},{"x":9.200000137090683,"y":-1.7164801326445178},{"x":9.600000143051147,"y":-2.0663829531000966},{"x":10.000000149011612,"y":-2.303263195398976},{"x":10.400000154972076,"y":-2.4220584344378793},{"x":10.800000160932541,"y":-2.423008547981569},{"x":11.200000166893005,"y":-2.311631591366753},{"x":11.60000017285347,"y":-2.0984463006758625},{"x":12.000000178813934,"y":-1.7984537041719664},{"x":12.400000184774399,"y":-1.4304024131327955},{"x":12.800000190734863,"y":-1.0158730835439638},{"x":13.200000196695328,"y":-0.5782267224206612},{"x":13.600000202655792,"y":-0.1414684761941679},{"x":14.000000208616257,"y":0.27091708501667755},{"x":14.400000214576721,"y":0.6371016210807599},{"x":14.800000220537186,"y":0.9379634107759546},{"x":15.20000022649765,"y":1.158000493807248},{"x":15.600000232458115,"y":1.2860614545042035},{"x":16.00000023841858,"y":1.3158579117488123},{"x":16.400000244379044,"y":1.2462337953371394},{"x":16.800000250339508,"y":1.0811787819897916},{"x":17.200000256299973,"y":0.8295862045153275},{"x":17.600000262260437,"y":0.5047686700399945},{"x":18.0000002682209,"y":0.12375686214701861},{"x":18.400000274181366,"y":-0.2935820740542503},{"x":18.80000028014183,"y":-0.725561119370187},{"x":19.200000286102295,"y":-1.1497727959020059},{"x":19.60000029206276,"y":-1.5442152566038878},{"x":20.000000298023224,"y":-1.8883877823888473},{"x":20.40000030398369,"y":-2.1643002583195754},{"x":20.800000309944153,"y":-2.357345890596292},{"x":21.200000315904617,"y":-2.456993720501516},{"x":21.600000321865082,"y":-2.457267006656001},{"x":22.000000327825546,"y":-2.3569847836242572},{"x":22.40000033378601,"y":-2.159756274274414},{"x":22.800000339746475,"y":-1.8737306879012103},{"x":23.20000034570694,"y":-1.5111176039026657},{"x":23.600000351667404,"y":-1.0875049604168288},{"x":24.00000035762787,"y":-0.6210120229221738},{"x":24.400000363588333,"y":-0.13132306139664746},{"x":24.800000369548798,"y":0.36134661564128145},{"x":25.200000375509262,"y":0.8372974390970223},{"x":25.600000381469727,"y":1.2783500507610344},{"x":26.00000038743019,"y":1.6687549633959304},{"x":26.400000393390656,"y":1.995960271973705},{"x":26.80000039935112,"y":2.251198107526756},{"x":27.200000405311584,"y":2.4298603940578563},{"x":27.60000041127205,"y":2.53164584966162},{"x":28.000000417232513,"y":2.560472458423572},{"x":28.400000423192978,"y":2.5241621763108353},{"x":28.800000429153442,"y":2.4339167569021587},{"x":29.200000435113907,"y":2.303614649659145},{"x":29.60000044107437,"y":2.1489683502030226},{"x":30.000000447034836,"y":1.986588872751097},{"x":30.4000004529953,"y":1.8330087879830594},{"x":30.800000458955765,"y":1.7037172781665813},{"x":31.20000046491623,"y":1.6122598057719524},{"x":31.600000470876694,"y":1.5694513248510225},{"x":32.00000047683716,"y":1.5827456882725852},{"x":32.40000048279762,"y":1.6557953589381555},{"x":32.80000048875809,"y":1.7882251797720157},{"x":33.20000049471855,"y":1.9756323513088283},{"x":33.600000500679016,"y":2.2098125284087686},{"x":34.00000050663948,"y":2.4791997327904767},{"x":34.400000512599945,"y":2.7694962371195695},{"x":34.80000051856041,"y":3.064458323799963},{"x":35.200000524520874,"y":3.346795402316367},{"x":35.60000053048134,"y":3.5991338295256448},{"x":36.0000005364418,"y":3.8049932423269173},{"x":36.40000054240227,"y":3.9497224673501505},{"x":36.80000054836273,"y":4.02134415483672},{"x":37.200000554323196,"y":4.011262080648194},{"x":37.60000056028366,"y":3.9147923142826246},{"x":38.000000566244125,"y":3.7314887748160688},{"x":38.40000057220459,"y":3.4652445944921753},{"x":38.800000578165054,"y":3.12416260221954},{"x":39.20000058412552,"y":2.720200495631612},{"x":39.60000059008598,"y":2.26860824144162}]},{"name":"7bb516f3-0fcd-465d-9050-7a478d61c3e2","values":[{"x":0,"y":0.9640899163683259},{"x":0.4000000059604645,"y":1.0566712151958535},{"x":0.800000011920929,"y":1.1057751798373754},{"x":1.2000000178813934,"y":1.106572002084292},{"x":1.600000023841858,"y":1.0563766981454226},{"x":2.0000000298023224,"y":0.9548531090382821},{"x":2.400000035762787,"y":0.8041057563500377},{"x":2.8000000417232513,"y":0.6086519441280631},{"x":3.200000047683716,"y":0.3752725408109544},{"x":3.6000000536441803,"y":0.11274612454802888},{"x":4.000000059604645,"y":-0.16852269656334112},{"x":4.400000065565109,"y":-0.45696429723599497},{"x":4.800000071525574,"y":-0.7403729633834089},{"x":5.200000077486038,"y":-1.0064784209726263},{"x":5.600000083446503,"y":-1.2435320015052715},{"x":6.000000089406967,"y":-1.4408782393988817},{"x":6.400000095367432,"y":-1.5894829497230936},{"x":6.800000101327896,"y":-1.6823905904676348},{"x":7.200000107288361,"y":-1.7150869010004408},{"x":7.600000113248825,"y":-1.685747280406921},{"x":8.00000011920929,"y":-1.5953569092865958},{"x":8.400000125169754,"y":-1.4476949508499568},{"x":8.800000131130219,"y":-1.249181971526421},{"x":9.200000137090683,"y":-1.008596649164275},{"x":9.600000143051147,"y":-0.7366745295119187},{"x":10.000000149011612,"y":-0.44560769895276514},{"x":10.400000154972076,"y":-0.14846944063052092},{"x":10.800000160932541,"y":0.14140804609382446},{"x":11.200000166893005,"y":0.41107217509432964},{"x":11.60000017285347,"y":0.648573008246677},{"x":12.000000178813934,"y":0.8435396151066956},{"x":12.400000184774399,"y":0.9876807422814421},{"x":12.800000190734863,"y":1.0751844436064417},{"x":13.200000196695328,"y":1.102996309808398},{"x":13.600000202655792,"y":1.0709619950756175},{"x":14.000000208616257,"y":0.9818265603914698},{"x":14.400000214576721,"y":0.8410903905094572},{"x":14.800000220537186,"y":0.6567287204594197},{"x":15.20000022649765,"y":0.43878875181786203},{"x":15.600000232458115,"y":0.19888458770930167},{"x":16.00000023841858,"y":-0.050384557567407895},{"x":16.400000244379044,"y":-0.296063487440528},{"x":16.800000250339508,"y":-0.5254856761909532},{"x":17.200000256299973,"y":-0.7269008377636502},{"x":17.600000262260437,"y":-0.8900536855509955},{"x":18.0000002682209,"y":-1.0066846776319833},{"x":18.400000274181366,"y":-1.0709275682946942},{"x":18.80000028014183,"y":-1.0795839128886842},{"x":19.200000286102295,"y":-1.0322610235318366},{"x":19.60000029206276,"y":-0.9313669183010742},{"x":20.000000298023224,"y":-0.7819631798429096},{"x":20.40000030398369,"y":-0.5914839568334641},{"x":20.800000309944153,"y":-0.3693362219675944},{"x":21.200000315904617,"y":-0.12640248439163432},{"x":21.600000321865082,"y":0.1255278744865836},{"x":22.000000327825546,"y":0.37436887395849683},{"x":22.40000033378601,"y":0.6083580079642834},{"x":22.800000339746475,"y":0.8166539363023708},{"x":23.20000034570694,"y":0.9898809149396808},{"x":23.600000351667404,"y":1.1205920034678714},{"x":24.00000035762787,"y":1.2036274590234708},{"x":24.400000363588333,"y":1.2363503202888788},{"x":24.800000369548798,"y":1.218747706262684},{"x":25.200000375509262,"y":1.1533934575316434},{"x":25.600000381469727,"y":1.0452750576858776},{"x":26.00000038743019,"y":0.9014949037244917},{"x":26.400000393390656,"y":0.7308625711668142},{"x":26.80000039935112,"y":0.5434003962811401},{"x":27.200000405311584,"y":0.3497891766139991},{"x":27.60000041127205,"y":0.16078383789384298},{"x":28.000000417232513,"y":-0.01336962743345127},{"x":28.400000423192978,"y":-0.1634848507234909},{"x":28.800000429153442,"y":-0.28192416168132806},{"x":29.200000435113907,"y":-0.36300020245354814},{"x":29.60000044107437,"y":-0.4032689996944525},{"x":30.000000447034836,"y":-0.4016992825281993},{"x":30.4000004529953,"y":-0.35970949635411975},{"x":30.800000458955765,"y":-0.28107106146463023},{"x":31.20000046491623,"y":-0.17168358337255402},{"x":31.600000470876694,"y":-0.03923455804225992},{"x":32.00000047683716,"y":0.10723773287610702},{"x":32.40000048279762,"y":0.2578542999477667},{"x":32.80000048875809,"y":0.4024507999992692},{"x":33.20000049471855,"y":0.5311480937216806},{"x":33.600000500679016,"y":0.6349071668207298},{"x":34.00000050663948,"y":0.7060384534108813},{"x":34.400000512599945,"y":0.7386380755937769},{"x":34.80000051856041,"y":0.7289274505929898},{"x":35.200000524520874,"y":0.6754779046338414},{"x":35.60000053048134,"y":0.5793080931047345},{"x":36.0000005364418,"y":0.44384883040157985},{"x":36.40000054240227,"y":0.2747770176511977},{"x":36.80000054836273,"y":0.07972734539964049},{"x":37.200000554323196,"y":-0.13210302969502136},{"x":37.60000056028366,"y":-0.3504359256492593},{"x":38.000000566244125,"y":-0.5644541896620485},{"x":38.40000057220459,"y":-0.7633756236385002},{"x":38.800000578165054,"y":-0.9370283250232297},{"x":39.20000058412552,"y":-1.0763968886101785},{"x":39.60000059008598,"y":-1.1741104334527532}]}],"marks":[{"type":"line","from":{"data":"626051fa-1747-428e-9f90-243461179615"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"#FF29D2"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}},{"type":"line","from":{"data":"7bb516f3-0fcd-465d-9050-7a478d61c3e2"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"blue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"626051fa-1747-428e-9f90-243461179615\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"626051fa-1747-428e-9f90-243461179615\", :field \"data.y\"}}], :axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :data ({:name \"626051fa-1747-428e-9f90-243461179615\", :values ({:x 0, :y 1.833554815635499} {:x 0.4000000059604645, :y 2.2390552702249624} {:x 0.800000011920929, :y 2.6730865933328976} {:x 1.2000000178813934, :y 3.1172248895352364} {:x 1.600000023841858, :y 3.5515347589227324} {:x 2.0000000298023224, :y 3.955471577860927} {:x 2.400000035762787, :y 4.308843300780938} {:x 2.8000000417232513, :y 4.592785208666024} {:x 3.200000047683716, :y 4.790699182617093} {:x 3.6000000536441803, :y 4.8891097028682395} {:x 4.000000059604645, :y 4.878391882141367} {:x 4.400000065565109, :y 4.75333232316776} {:x 4.800000071525574, :y 4.513491202072583} {:x 5.200000077486038, :y 4.163343364119166} {:x 5.600000083446503, :y 3.7121869188109953} {:x 6.000000089406967, :y 3.1738193038898252} {:x 6.400000095367432, :y 2.5659924694521297} {:x 6.800000101327896, :y 1.9096701122659172} {:x 7.200000107288361, :y 1.2281201769258034} {:x 7.600000113248825, :y 0.5458845890176197} {:x 8.00000011920929, :y -0.11232507666541858} {:x 8.400000125169754, :y -0.722752936395855} {:x 8.800000131130219, :y -1.2636903749802397} {:x 9.200000137090683, :y -1.7164801326445178} {:x 9.600000143051147, :y -2.0663829531000966} {:x 10.000000149011612, :y -2.303263195398976} {:x 10.400000154972076, :y -2.4220584344378793} {:x 10.800000160932541, :y -2.423008547981569} {:x 11.200000166893005, :y -2.311631591366753} {:x 11.60000017285347, :y -2.0984463006758625} {:x 12.000000178813934, :y -1.7984537041719664} {:x 12.400000184774399, :y -1.4304024131327955} {:x 12.800000190734863, :y -1.0158730835439638} {:x 13.200000196695328, :y -0.5782267224206612} {:x 13.600000202655792, :y -0.1414684761941679} {:x 14.000000208616257, :y 0.27091708501667755} {:x 14.400000214576721, :y 0.6371016210807599} {:x 14.800000220537186, :y 0.9379634107759546} {:x 15.20000022649765, :y 1.158000493807248} {:x 15.600000232458115, :y 1.2860614545042035} {:x 16.00000023841858, :y 1.3158579117488123} {:x 16.400000244379044, :y 1.2462337953371394} {:x 16.800000250339508, :y 1.0811787819897916} {:x 17.200000256299973, :y 0.8295862045153275} {:x 17.600000262260437, :y 0.5047686700399945} {:x 18.0000002682209, :y 0.12375686214701861} {:x 18.400000274181366, :y -0.2935820740542503} {:x 18.80000028014183, :y -0.725561119370187} {:x 19.200000286102295, :y -1.1497727959020059} {:x 19.60000029206276, :y -1.5442152566038878} {:x 20.000000298023224, :y -1.8883877823888473} {:x 20.40000030398369, :y -2.1643002583195754} {:x 20.800000309944153, :y -2.357345890596292} {:x 21.200000315904617, :y -2.456993720501516} {:x 21.600000321865082, :y -2.457267006656001} {:x 22.000000327825546, :y -2.3569847836242572} {:x 22.40000033378601, :y -2.159756274274414} {:x 22.800000339746475, :y -1.8737306879012103} {:x 23.20000034570694, :y -1.5111176039026657} {:x 23.600000351667404, :y -1.0875049604168288} {:x 24.00000035762787, :y -0.6210120229221738} {:x 24.400000363588333, :y -0.13132306139664746} {:x 24.800000369548798, :y 0.36134661564128145} {:x 25.200000375509262, :y 0.8372974390970223} {:x 25.600000381469727, :y 1.2783500507610344} {:x 26.00000038743019, :y 1.6687549633959304} {:x 26.400000393390656, :y 1.995960271973705} {:x 26.80000039935112, :y 2.251198107526756} {:x 27.200000405311584, :y 2.4298603940578563} {:x 27.60000041127205, :y 2.53164584966162} {:x 28.000000417232513, :y 2.560472458423572} {:x 28.400000423192978, :y 2.5241621763108353} {:x 28.800000429153442, :y 2.4339167569021587} {:x 29.200000435113907, :y 2.303614649659145} {:x 29.60000044107437, :y 2.1489683502030226} {:x 30.000000447034836, :y 1.986588872751097} {:x 30.4000004529953, :y 1.8330087879830594} {:x 30.800000458955765, :y 1.7037172781665813} {:x 31.20000046491623, :y 1.6122598057719524} {:x 31.600000470876694, :y 1.5694513248510225} {:x 32.00000047683716, :y 1.5827456882725852} {:x 32.40000048279762, :y 1.6557953589381555} {:x 32.80000048875809, :y 1.7882251797720157} {:x 33.20000049471855, :y 1.9756323513088283} {:x 33.600000500679016, :y 2.2098125284087686} {:x 34.00000050663948, :y 2.4791997327904767} {:x 34.400000512599945, :y 2.7694962371195695} {:x 34.80000051856041, :y 3.064458323799963} {:x 35.200000524520874, :y 3.346795402316367} {:x 35.60000053048134, :y 3.5991338295256448} {:x 36.0000005364418, :y 3.8049932423269173} {:x 36.40000054240227, :y 3.9497224673501505} {:x 36.80000054836273, :y 4.02134415483672} {:x 37.200000554323196, :y 4.011262080648194} {:x 37.60000056028366, :y 3.9147923142826246} {:x 38.000000566244125, :y 3.7314887748160688} {:x 38.40000057220459, :y 3.4652445944921753} {:x 38.800000578165054, :y 3.12416260221954} {:x 39.20000058412552, :y 2.720200495631612} {:x 39.60000059008598, :y 2.26860824144162})} {:name \"7bb516f3-0fcd-465d-9050-7a478d61c3e2\", :values ({:x 0, :y 0.9640899163683259} {:x 0.4000000059604645, :y 1.0566712151958535} {:x 0.800000011920929, :y 1.1057751798373754} {:x 1.2000000178813934, :y 1.106572002084292} {:x 1.600000023841858, :y 1.0563766981454226} {:x 2.0000000298023224, :y 0.9548531090382821} {:x 2.400000035762787, :y 0.8041057563500377} {:x 2.8000000417232513, :y 0.6086519441280631} {:x 3.200000047683716, :y 0.3752725408109544} {:x 3.6000000536441803, :y 0.11274612454802888} {:x 4.000000059604645, :y -0.16852269656334112} {:x 4.400000065565109, :y -0.45696429723599497} {:x 4.800000071525574, :y -0.7403729633834089} {:x 5.200000077486038, :y -1.0064784209726263} {:x 5.600000083446503, :y -1.2435320015052715} {:x 6.000000089406967, :y -1.4408782393988817} {:x 6.400000095367432, :y -1.5894829497230936} {:x 6.800000101327896, :y -1.6823905904676348} {:x 7.200000107288361, :y -1.7150869010004408} {:x 7.600000113248825, :y -1.685747280406921} {:x 8.00000011920929, :y -1.5953569092865958} {:x 8.400000125169754, :y -1.4476949508499568} {:x 8.800000131130219, :y -1.249181971526421} {:x 9.200000137090683, :y -1.008596649164275} {:x 9.600000143051147, :y -0.7366745295119187} {:x 10.000000149011612, :y -0.44560769895276514} {:x 10.400000154972076, :y -0.14846944063052092} {:x 10.800000160932541, :y 0.14140804609382446} {:x 11.200000166893005, :y 0.41107217509432964} {:x 11.60000017285347, :y 0.648573008246677} {:x 12.000000178813934, :y 0.8435396151066956} {:x 12.400000184774399, :y 0.9876807422814421} {:x 12.800000190734863, :y 1.0751844436064417} {:x 13.200000196695328, :y 1.102996309808398} {:x 13.600000202655792, :y 1.0709619950756175} {:x 14.000000208616257, :y 0.9818265603914698} {:x 14.400000214576721, :y 0.8410903905094572} {:x 14.800000220537186, :y 0.6567287204594197} {:x 15.20000022649765, :y 0.43878875181786203} {:x 15.600000232458115, :y 0.19888458770930167} {:x 16.00000023841858, :y -0.050384557567407895} {:x 16.400000244379044, :y -0.296063487440528} {:x 16.800000250339508, :y -0.5254856761909532} {:x 17.200000256299973, :y -0.7269008377636502} {:x 17.600000262260437, :y -0.8900536855509955} {:x 18.0000002682209, :y -1.0066846776319833} {:x 18.400000274181366, :y -1.0709275682946942} {:x 18.80000028014183, :y -1.0795839128886842} {:x 19.200000286102295, :y -1.0322610235318366} {:x 19.60000029206276, :y -0.9313669183010742} {:x 20.000000298023224, :y -0.7819631798429096} {:x 20.40000030398369, :y -0.5914839568334641} {:x 20.800000309944153, :y -0.3693362219675944} {:x 21.200000315904617, :y -0.12640248439163432} {:x 21.600000321865082, :y 0.1255278744865836} {:x 22.000000327825546, :y 0.37436887395849683} {:x 22.40000033378601, :y 0.6083580079642834} {:x 22.800000339746475, :y 0.8166539363023708} {:x 23.20000034570694, :y 0.9898809149396808} {:x 23.600000351667404, :y 1.1205920034678714} {:x 24.00000035762787, :y 1.2036274590234708} {:x 24.400000363588333, :y 1.2363503202888788} {:x 24.800000369548798, :y 1.218747706262684} {:x 25.200000375509262, :y 1.1533934575316434} {:x 25.600000381469727, :y 1.0452750576858776} {:x 26.00000038743019, :y 0.9014949037244917} {:x 26.400000393390656, :y 0.7308625711668142} {:x 26.80000039935112, :y 0.5434003962811401} {:x 27.200000405311584, :y 0.3497891766139991} {:x 27.60000041127205, :y 0.16078383789384298} {:x 28.000000417232513, :y -0.01336962743345127} {:x 28.400000423192978, :y -0.1634848507234909} {:x 28.800000429153442, :y -0.28192416168132806} {:x 29.200000435113907, :y -0.36300020245354814} {:x 29.60000044107437, :y -0.4032689996944525} {:x 30.000000447034836, :y -0.4016992825281993} {:x 30.4000004529953, :y -0.35970949635411975} {:x 30.800000458955765, :y -0.28107106146463023} {:x 31.20000046491623, :y -0.17168358337255402} {:x 31.600000470876694, :y -0.03923455804225992} {:x 32.00000047683716, :y 0.10723773287610702} {:x 32.40000048279762, :y 0.2578542999477667} {:x 32.80000048875809, :y 0.4024507999992692} {:x 33.20000049471855, :y 0.5311480937216806} {:x 33.600000500679016, :y 0.6349071668207298} {:x 34.00000050663948, :y 0.7060384534108813} {:x 34.400000512599945, :y 0.7386380755937769} {:x 34.80000051856041, :y 0.7289274505929898} {:x 35.200000524520874, :y 0.6754779046338414} {:x 35.60000053048134, :y 0.5793080931047345} {:x 36.0000005364418, :y 0.44384883040157985} {:x 36.40000054240227, :y 0.2747770176511977} {:x 36.80000054836273, :y 0.07972734539964049} {:x 37.200000554323196, :y -0.13210302969502136} {:x 37.60000056028366, :y -0.3504359256492593} {:x 38.000000566244125, :y -0.5644541896620485} {:x 38.40000057220459, :y -0.7633756236385002} {:x 38.800000578165054, :y -0.9370283250232297} {:x 39.20000058412552, :y -1.0763968886101785} {:x 39.60000059008598, :y -1.1741104334527532})}), :marks ({:type \"line\", :from {:data \"626051fa-1747-428e-9f90-243461179615\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"#FF29D2\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}} {:type \"line\", :from {:data \"7bb516f3-0fcd-465d-9050-7a478d61c3e2\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"blue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}})}}"}
;; <=

;; @@

;; @@
