(defproject org.zalando.stups/mint-storage "0.13.0-SNAPSHOT"
  :description "The secret rotator and distributor."
  :url "https://github.com/zalando-stups/mint"

  :license {:name "The Apache License, Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0"}

  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.zalando.stups/friboo "1.9.0"]
                 [yesql "0.5.1"]]

  :main ^:skip-aot org.zalando.stups.mint.storage.core
  :uberjar-name "mint-storage.jar"

  :plugins [[io.sarnowski/lein-docker "1.1.0"]
            [org.zalando.stups/lein-scm-source "0.2.0"]]

  :docker {:image-name #=(eval (str (some-> (System/getenv "DEFAULT_DOCKER_REGISTRY")
                                            (str "/"))
                                    "stups/mint-storage"))}

  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["clean"]
                  ["uberjar"]
                  ["scm-source"]
                  ["docker" "build"]
                  ["docker" "push"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]

  :pom-addition [:developers
                 [:developer {:id "sarnowski"}
                  [:name "Tobias Sarnowski"]
                  [:email "tobias.sarnowski@zalando.de"]
                  [:role "Maintainer"]]]

  :profiles {:uberjar {:aot :all}

             :dev     {:repl-options {:init-ns user}
                       :source-paths ["dev"]
                       :dependencies [[org.clojure/tools.namespace "0.2.10"]
                                      [org.clojure/java.classpath "0.2.2"]]}})
