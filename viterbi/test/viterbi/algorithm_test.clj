(ns viterbi.algorithm-test
  (:require [clojure.test :refer :all]
            [viterbi.algorithm :refer :all])
  (:require [viterbi.bigrams :as bi])
  (:require [viterbi.lexicon :as lex]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

 (deftest maxValtest
   (testing "Tests von Methoden, welche mit der Methode maxVal in vorkommen oder von maxVal selbst"
    (is (= (mapVal (fn [k, v] (* v (* k 2)) {2 0.04000000000000001}) {2 (* 0.04000000000000001 4)} 1 )))
    (is (=   (maxVal (keys (hash-map "AuxV" 0.0072, "KopV" 0.009))
        0.2
        {"AuxV" 0.2 "KopV" 0.2}
       ["a" 0], (hash-map "AuxV" 0.0072 "KopV" 0.009)) ["KopV" 0.000360]))))

   (deftest viterbitest
     (testing "Tests zum Viterbi-Algorithmus"
    (is (= (get-ins {"a" {"ab" 2 "ac" 3} "b" {"ab" 4 "d" 5}} '("a" "b") "ab") (hash-map "b" 4 "a" 2)))
    (is (= (viterPos "<s>" '("<s>" "wir" "werden" "geschickt" "." "</s>") shortEmission shortBigram {} {"<s>" 1})
           [{"wir" {"PPER" 0.06}, "geschickt" {"ADJD" 3.6E-4, "VVPP" 5.760000000000001E-4},
           "</s>" {"</s>" 3.6E-5}, "werden" {"VVFIN" 0.009, "VAINF" 0.0072}, "." {"$." 3.6E-5},
           "<s>" {"<s>" 1}}
           {["werden" "VVFIN"] ["wir" "PPER"], ["werden" "VAINF"] ["wir" "PPER"],
           ["wir" "PPER"] ["<s>" "<s>"], ["geschickt" "ADJD"] ["werden" "VVFIN"],
           ["geschickt" "VVPP"] ["werden" "VAINF"], ["." "$."] ["geschickt" "ADJD"],
           ["</s>" "</s>"] ["." "$."]}] ))))

    (deftest backtrackerTest
      (testing "Tests zum backtracking"
      (is (= (backtracker {["werden" "VVFIN"] ["wir" "PPER"], ["werden" "VAINF"] ["wir" "PPER"],
              ["wir" "PPER"] ["<s>" "<s>"], ["geschickt" "ADJD"] ["werden" "VVFIN"],
              ["geschickt" "VVPP"] ["werden" "VAINF"], ["." "$."] ["geschickt" "ADJD"],
              ["</s>" "</s>"] ["." "$."]} ["geschickt" "ADJD"])
          (vector "<s>" "PPER" "VVFIN" "ADJD")))
       (is (= (bestSeq '("<s>" "wir" "werden" "geschickt" "." "</s>") shortEmission
                 {["werden" "VVFIN"] ["wir" "PPER"], ["werden" "VAINF"] ["wir" "PPER"],
                  ["wir" "PPER"] ["<s>" "<s>"], ["geschickt" "ADJD"] ["werden" "VVFIN"],
                  ["geschickt" "VVPP"] ["werden" "VAINF"], ["." "$."] ["geschickt" "ADJD"],
                  ["</s>" "</s>"] ["." "$."]})
               (vector "<s>" "PPER" "VVFIN" "ADJD" "$." "</s>")))))
