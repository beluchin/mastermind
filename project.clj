(defproject mastermind "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; flycheck-clojure setup
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.typed "0.5.3"  :classifier "slim"]]
  ;; Use lein-environ plugin if you want to set checker configuration in profile.
  :plugins [[lein-environ "1.1.0"]]
  ;; Configuration here may be overridden by namespace metadata.
  :profiles {:dev {:env {:squiggly {:checkers [:eastwood :typed :kibit]
                                    :eastwood-exclude-linters [:unlimited-use]
                                    :eastwood-options {:add-linters [:unused-fn-args]
                                                       ;; :builtin-config-files ["myconfigfile.clj"]
                                                       }}}}}
  :main mastermind.core

  ; to evaluate inside defproject ...
  ; https://stackoverflow.com/a/7739179/614800
  :target-path ~(str (let [os (System/getProperty "os.name")
                           is-windows (re-find #"(?i)windows" os)]
                       (if is-windows
                         "c:\\temp"
                         "/Users/beluchin/tmp"

                         ; TODO: this is failing and I don't know why
                         ; fix it to avoid hard-coding the paths above
                         #_(System/getenv "TEMP")

                         ))

                     "/Users/beluchin/tmp"
                     "/mastermind___/target"))
