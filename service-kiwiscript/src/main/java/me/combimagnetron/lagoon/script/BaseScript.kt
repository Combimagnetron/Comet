package me.combimagnetron.lagoon.script

import me.combimagnetron.lagoon.Comet
import me.combimagnetron.lagoon.communication.message.impl.servicebound.ServiceBoundRequestInstanceBlueprintsMessage
import me.combimagnetron.lagoon.data.Identifier
import me.combimagnetron.lagoon.event.Event
import me.combimagnetron.lagoon.instance.InstanceBlueprint
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import java.lang.IllegalArgumentException
import java.nio.file.Path
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

open class BaseScript {
    private val functionRegistry = FunctionRegistry()
    val nil = Function.Type(null)
    val text = Function.Type(String)
    val number = Function.Type(0)

    fun test() : BaseScript {
        return script {
            use {
                block("example_block") from "combimagnetron/examples"
                condition("example_condition") from "combimagnetron/examples"
                function("basket_imported_example_function") from basket("example_basket_repo:utils/network")
                function("example_function") from "combimagnetron/examples/functions"
            }

            kiwi {
                name = "example"
                author = "Combimagnetron"
                reader = "EventScript.kt"
            }

            "blueprint_block_impl" follows "blueprint_block"

            block {
                "example_block" {
                    public function "example" wants arguments(Argument(), Argument()) whenever conditions(Condition(), Condition()) returns nil does {
                        val text = text("a")
                        val num = number(text.length + 6)
                        returns(text + num)
                    }
                }

                blueprint block "blueprint_block" with {
                    constant field "branch_name" stays Identifier.of("Cosmorise", "comet")

                    guarded function "latest_version" wants arguments(Argument()) returns text does {
                        val versionCollection = InstanceBlueprint.request(field("branch_name"))
                        val blueprint = versionCollection.newest()
                    }

                    required function "required" returns text
                }

            }

            event {
                "example" listens_to bukkit_event(PlayerMoveEvent::class) whenever conditions(Condition()) returns nil does {
                    function("we_the_best_music")
                    val text = (function("another_example_function") with arguments(Argument(), Argument())) as String
                    val num = number(text.length)
                }
            }

            function {
                "another_example_function" wants arguments(TextArgument()) returns text does {
                    val entity = Bukkit.getWorld("world")?.getEntity(UUID.randomUUID())
                    entity?.customName(Component.text("aaaa"))
                    returns(text(entity?.customName))
                }
                "we_the_best_music" wants arguments(PlayerArgument(), TextArgument()) returns nil does {
                    function("another_example_function") with arguments(PlayerArgument(Bukkit.getOnlinePlayers().first()))
                    val player = arguments[0] as Player
                    val text = arguments[1] as String
                }
            }

        }

    }

    infix fun String.follows(value: String) {

    }

    class ExampleEvent : Event {
        override fun eventType(): Class<out Event> {
            return this.javaClass
        }

    }

    class PlayerArgument() : Argument() {
        constructor(value : Player?) : this()
    }

    class TextArgument() : Argument() {
        constructor(value : String) : this()
    }

    class BukkitEvent(private var clazz : KClass<out org.bukkit.event.Event>) : Event {

        override fun eventType(): Class<out Event> {
            return this.javaClass
        }
    }

    class InfoBuilder {
        var name : String = ""
        var author : String = ""
        var reader : String = "BaseScript.kt"
    }

    class DependencyBuilder {
        private lateinit var path : Path

        open class Selector(private var operator : String)

        class Block(private var name : String) : Selector("block")

        class Function(private var name : String) : Selector("func")

        class Condition(private var name : String) : Selector("condition")

        fun block(name : String) : Block {
            return Block(name)
        }

        fun function(name : String) : Function {
            return Function(name)
        }

        fun condition(name : String) : Condition {
            return Condition(name)
        }

        fun basket(path : String) : String {
            return ""
        }

        infix fun Selector.from(value: String) {
            path = Path.of(value)
        }

    }

    class EventBlock : Function() {
        private lateinit var event : Event

        infix fun String.listens_to(value : Event) {
            event = value
        }

        fun bukkit_event(clazz : KClass<out org.bukkit.event.Event>) : BukkitEvent {
            return BukkitEvent(clazz)
        }

    }

    open class Function {
        private val conditions: MutableList<Condition> = mutableListOf()
        private val arguments: MutableList<Argument> = mutableListOf()
        private var returnType: Type = Type(Any())
        private lateinit var functioninfo: FunctionInfo

        infix fun Any?.returns(value: Type) {
            returnType = value
        }

        infix fun Unit.does(value : DoBlockBuilder.() -> Unit) : DoBlockBuilder {
            return DoBlockBuilder(returnType, arguments)
        }

        infix fun Any?.wants(value : Arguments) : Arguments {
            arguments.addAll(value.list)
            return value
        }

        infix fun Any?.whenever(value : Conditions) : Conditions {
            conditions.addAll(value.list)
            return value
        }

        infix fun Function?.with(value : Arguments) : Any? {

            return null
        }

        fun arguments(vararg arguments : Argument) : Arguments {
            return Arguments(*arguments)
        }

        fun conditions(vararg conditions : Condition) : Conditions {
            return Conditions(*conditions)
        }

        fun function(id : String) : Function? {
            val function : Function = this
            return null
        }

        class Type(val type : Any?)

        class FunctionIdentifierDirty
    }

    class BlockRegistryBuilder {
        val function = Function.FunctionIdentifierDirty()
        val blueprint = BlueprintDummy()

        operator fun String.invoke(initializer: Block.() -> Unit) : Block {
            return Block().apply(initializer)
        }

        infix fun Any?.with(value : BlueprintBlock.() -> Unit) : BlueprintBlock {
            return BlueprintBlock().apply(value)
        }

        infix fun BlueprintDummy.block(value : Any?) : Any? {
            return null
        }

        open class Block : Function() {
            var fields : MutableMap<String, Any> = mutableMapOf();
            var public = Blueprint.TypeAccessLevel.PublicTypeAccessLevel()
            var guarded = Blueprint.TypeAccessLevel.GuardedTypeAccessLevel()
            var required = Blueprint.TypeAccessLevel.RequiredTypeAccessLevel()
            var constant = Blueprint.Constant()
            var requiredDummy = RequiredDummy()

            fun <T> field(value : String) : T {
                return fields.get(value)!! as T
            }

            infix fun Blueprint.TypeAccessLevel.function(value: String) : String {
                return String()
            }

            infix fun String.invoke(initializer: Function.() -> Unit) : Function {
                return Function().apply(initializer)
            }

            infix fun Blueprint.TypeAccessLevel.RequiredTypeAccessLevel.function(value : String) : RequiredDummy {
                return requiredDummy
            }

            infix fun RequiredDummy.returns(value : Function.Type) {

            }

            infix fun Blueprint.Constant.field(value : String) : ConstantDummy {
                return ConstantDummy(value)
            }

            infix fun ConstantDummy.stays(value : Any) {
                fields.put(this.value, value)
            }

            class RequiredDummy

            class ConstantDummy(var value : String)

        }

        class BlueprintDummy {

        }

        class Blueprint {

            open class TypeAccessLevel {
                class PublicTypeAccessLevel : TypeAccessLevel()
                class GuardedTypeAccessLevel : TypeAccessLevel()
                class RequiredTypeAccessLevel : TypeAccessLevel()
            }

            class Constant
        }

        class BlueprintBlock : Block() {



        }

    }


    class FunctionRegistryBuilder : Function()

    class FunctionInfo(var author: String, var projectName: String)

    class FunctionRegistry : Registry<Function>() {



    }

    class BlueprintRegistry : Registry<BlockRegistryBuilder.Blueprint>() {

    }

    abstract class Registry<T>() {
        private var collection: MutableCollection<T> = mutableSetOf();

    }

    class Arguments(vararg arguments: Argument) {
        var list: MutableList<Argument> = mutableListOf()

        init {
            list.addAll(arguments)
        }
    }

    open class Argument

    class Conditions(vararg conditions: Condition) {
        var list: MutableList<Condition> = mutableListOf()

        init {
            list.addAll(conditions)
        }
    }

    class Condition

    class DoBlockBuilder(private val returnType : BaseScript.Function.Type, private val value : List<Argument>) {
        private var returned: Any? = null
        var arguments: List<Argument> = value

        fun returns(value : Any) {
            if (value.javaClass != returnType.javaClass) {
                throw IllegalArgumentException("Returned type doesn't match with the specified and expected return type!")
            }
            returned = value
        }
    }

    fun script(initializer: BaseScript.() -> Unit) : BaseScript {
        return BaseScript().apply(initializer)
    }

    fun use(initializer: DependencyBuilder.() -> Unit) : DependencyBuilder {
        return DependencyBuilder().apply(initializer)
    }

    fun kiwi(initializer: InfoBuilder.() -> Unit) : InfoBuilder {
        return InfoBuilder().apply(initializer)
    }

    fun block(initializer: BlockRegistryBuilder.() -> Unit) : BlockRegistryBuilder {
        return BlockRegistryBuilder().apply(initializer)
    }

    fun function(initializer: FunctionRegistryBuilder.() -> Unit) : FunctionRegistryBuilder {
        return FunctionRegistryBuilder().apply(initializer)
    }

    fun event(initializer: EventBlock.() -> Unit) : EventBlock {
        return EventBlock().apply(initializer)
    }

    fun text(string: String?) : String {
        return string!!
    }

    fun number(number: Number) : Number {
        return number
    }

}


