package me.combimagnetron.lagoon.script

import me.combimagnetron.lagoon.operation.Operation
import me.combimagnetron.lagoon.operation.Operation.SimpleOperation
import me.combimagnetron.lagoon.operation.Operation.SimpleOperationConsumer

class TestScript : BaseScript() {

    class Test()

    fun test(initializer: Test.() -> Unit) : Test {
        return Test().apply(initializer)
    }


    fun a() : BaseScript {
        return script {
            test {

            }

            kiwi {
                author = "Combimagnetron"
                name = "TestScript"
            }

            block {
                "block_example" {
                    "func" returns nil does {

                    }
                }
            }

            event {

            }

            function {
                "aa" wants arguments() whenever conditions() returns nil does {
                    function("example")
                }
            }

        }
    }


}