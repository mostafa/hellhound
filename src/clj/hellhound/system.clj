(ns hellhound.system
  "Systems are the most important thing in the **HellHound** ecosystem.
  Systems define how your application should work."
  ^{:author "Sameer Rahmani (@lxsameer)"}
  (:require [hellhound.config              :as config]
            [hellhound.system.core         :as core]
            [hellhound.system.workflow     :as workflow]
            [hellhound.logger              :as logger]
            [hellhound.config.defaults     :as default]))

(defn set-system!
  "Set the default system.

  TODO: more doc"
  {:added      1.0
   :public-api true}
  [system]
  (core/set-system! system))

(defn system
  "Returns the processed system.

  TODO: more doc"
  {:added      1.0
   :public-api true}
  []
  (core/get-system))

(defn start!
  "Starts the default system by calling start on all the components.

  TODO: more doc"
  {:added      1.0
   :public-api true}
  []
  ;; Read the configuration for the current runtime environment which
  ;; specified by `HH_ENV` environment. Default env is `:development`
  (config/load-runtime-configuration)
  (logger/init! (config/get-config :logger))
  (core/start-system! @core/system)
  (workflow/setup @core/system))

(defn stop!
  "Stops the default system.

  TODO: more doc"
  {:added      1.0
   :public-api true}
  []
  (core/stop-system! @core/system))

(defn get-component
  "Finds and returns the component with the given `name`.

  TODO: more doc"
  {:added      1.0
   :public-api true}
  [name]
  (get (:components (core/get-system)) name))

(defn defcomponent
  "A short cut function to create a component map with the given details.

  TODO: more doc"
  {:added      1.0
   :public-api true}
  ([component-name start-fn stop-fn]
   (defcomponent component-name start-fn stop-fn []))

  ([component-name start-fn stop-fn dependencies]
   {:hellhound.component/name component-name
    :hellhound.component/start-fn start-fn
    :hellhound.component/stop-fn stop-fn
    :hellhound.component/depends-on dependencies}))
