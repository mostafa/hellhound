(defproject codamic/hellhound "1.0.0-SNAPSHOT"
  :description "A simple full-stack web framework for clojure on top of Pnedestal"
  :license     {"mit"
                "https://opensource.org/licenses/MIT"}
  :url         "http://github.com/Codamic/hellhound"
  :scm         {:url "https://github.com/Codamic/hellhound"}

  :exclusions [ch.qos.logback/logback-classic]

  :dependencies [[org.clojure/clojure        "1.9.0-alpha17"]
                 [org.clojure/clojurescript  "1.9.562"]
                 [org.clojure/core.async     "0.3.443"]

                 ;; Logging
                 [org.slf4j/slf4j-api              "1.7.25"]
                 [com.taoensso/timbre              "4.10.0"]
                 [com.fzakaria/slf4j-timbre        "0.3.7"]
                 [org.slf4j/log4j-over-slf4j       "1.7.25"]
                 [org.slf4j/jul-to-slf4j           "1.7.25"]
                 [org.slf4j/jcl-over-slf4j         "1.7.25"]

                 [io.aviso/pretty                  "0.1.34"]

                 [io.pedestal/pedestal.service "0.5.2"]
                 ;; TODO: Move the adapters to seperate jars. For example hellhound.jetty
                 ;;[io.pedestal/pedestal.jetty "0.5.2"]
                 [io.pedestal/pedestal.immutant "0.5.2"]

                 ;; [io.pedestal/pedestal.tomcat "0.5.2"]

                 [io.pedestal/pedestal.interceptor "0.5.2"]
                 [io.pedestal/pedestal.route       "0.5.2"]

                 [danlentz/clj-uuid                "0.1.7"]
                 ;; TODO: We have to move these stuff into a different jar
                 ;; to avoid unnecessary jar installation

                 ;; Cassandra adapter
                 ;;[cc.qbits/alia              "4.0.0-beta10"]
                 ;; -------------------------------------------------------

                 ;; Time calculation
                 [clj-time                   "0.13.0"]


                 ;; UI
                 [reagent                    "0.6.2"]
                 [re-frame                   "0.9.4"]
                 ;; Client Side routeing
                 [secretary                  "1.2.3"]
                 [re-frisk                   "0.4.5"]

                 ;; I18n
                 [com.taoensso/tempura       "1.1.2"]

                 ;; Transit support
                 [com.cognitect/transit-clj  "0.8.300"]
                 [com.cognitect/transit-cljs "0.8.239"]

                 ;; Template Engine
                 [selmer                     "1.10.7"]

                 ;; JSON Parser
                 [cheshire                   "5.7.1"]]

  :plugins [[lein-cljsbuild "1.1.3"]]




  :min-lein-version "2.6.1"

  :source-paths ["src/clj" "src/cljs" "src/cljc"]

  :test-paths ["test/clj" "test/cljc"]
  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js"]

  :uberjar-name "hellhound.jar"

  ;; nREPL by default starts in the :main namespace, we want to start in `user`
  ;; because that's where our development helper functions like (run) and
  ;; (browser-repl) live.
  :repl-options {:init-ns user}

  :cljsbuild {:builds
              [
               ;; {:id "app"
               ;;  :source-paths ["src/cljs" "src/cljc"]

               ;;  :figwheel true

               ;;  ;; Alternatively, you can configure a function to run every time figwheel reloads.
               ;;  ;; :figwheel {:on-jsload "hellhound.core/on-figwheel-reload"}

               ;;  :compiler {:main hellhound.core
               ;;             :asset-path "js/compiled/out"
               ;;             :output-to "resources/public/js/compiled/hellhound.js"
               ;;             :output-dir "resources/public/js/compiled/out"
               ;;             :source-map-timestamp true}}

               {:id "test"
                :source-paths ["src/cljs" "test/cljs" "src/cljc" "test/cljc"]
                :compiler {:output-to "resources/public/js/compiled/testable.js"
                           :main hellhound.test-runner
                           :optimizations :none}}

               {:id "optimized-test"
                :source-paths ["src/cljs" "test/cljs" "src/cljc" "test/cljc"]
                :compiler {:output-to "resources/public/js/compiled/testable.js"
                           :main hellhound.test-runner
                           :optimizations :advance}}]}

  ;; {:id "min"
  ;;  :source-paths ["src/cljs" "src/cljc"]
  ;;  :jar true
  ;;  :compiler {:main hellhound.core
  ;;             :output-to "resources/public/js/compiled/hellhound.js"
  ;;             :output-dir "target"
  ;;             :source-map-timestamp true
  ;;             :optimizations :advanced
  ;;             :pretty-print false}}


  :doo {:build "test"}

  :profiles {:dev
             {:dependencies [[figwheel                   "0.5.10"]
                             [figwheel-sidecar           "0.5.10"]
                             [funcool/codeina            "0.5.0"]
                             [com.cemerick/piggieback    "0.2.2"]
                             [org.clojure/tools.nrepl    "0.2.13"]]

              :plugins [[lein-figwheel  "0.5.4-4"]
                        [lein-doo       "0.1.6"]]

              :source-paths ["dev"]
              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}

             :uberjar
             {:source-paths ^:replace ["src/clj" "src/cljc"]
              :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
              :hooks []
              :omit-source true
              :aot :all}

             :test {:dependencies [[clj-http "3.5.0"]]}})
