package me.combimagnetron.comet.internal.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.DataObject;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.Item;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.comet.service.ServiceFile;
import me.combimagnetron.comet.service.broker.BrokerAgreement;
import me.combimagnetron.comet.util.ProtocolUtil;
import me.combimagnetron.comet.util.Values;
import me.combimagnetron.comet.util.Version;
import org.jglrxavpok.hephaistos.nbt.CompressedProcesser;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.NBTReader;
import org.jglrxavpok.hephaistos.nbt.NBTWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ByteBuffer {
    private ByteArrayDataInput byteArrayDataInput;
    private ByteArrayDataOutput byteArrayDataOutput;
    public static ByteBuffer of(byte[] bytes) {
        return new ByteBuffer(bytes);
    }
    public static ByteBuffer empty() {
        return new ByteBuffer();
    }
    private ByteBuffer(byte[] bytes) {
        this.byteArrayDataInput = ByteStreams.newDataInput(bytes);
    }
    private ByteBuffer() {
        this.byteArrayDataOutput = ByteStreams.newDataOutput();
    }
    public <T> ByteBuffer write(Adapter<T> type, T object) {
        type.write(byteArrayDataOutput, object);
        return this;
    }

    public ByteBuffer write(byte[] bytes) {
        byteArrayDataOutput.write(bytes);
        return this;
    }

    public <T> T read(Adapter<T> type) {
        return type.read(byteArrayDataInput);
    }
    public <T> void writeCollection(Adapter<T> type, Collection<T> collection) {
        write(Adapter.VAR_INT, collection.size());
        for (T t : collection) {
            write(type, t);
        }
    }

    public <T extends Writeable> void writeCollection(Collection<T> collection) {
        write(Adapter.VAR_INT, collection.size());
        for (T t : collection) {
            t.write(this);
        }
    }

    public <T extends Enum<?>> T readEnum(Class<T> clazz) {
        return clazz.getEnumConstants()[read(Adapter.VAR_INT)];
    }

    public <T> Collection<T> readCollection(Function<ByteBuffer, T> function) {
        final int size = read(Adapter.VAR_INT);
        final List<T> values = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            values.add(function.apply(this));
        }
        return values;
    }

    public <T> Collection<T> readCollection(Adapter<T> type, Supplier<Collection<T>> collectionSupplier) {
        Collection<T> collection = collectionSupplier.get();
        int size = read(Adapter.INT);
        for (int i = 0; i < size; i++) {
            collection.add(read(type));
        }
        return collection;
    }

    public ByteArrayDataOutput output() {
        return byteArrayDataOutput;
    }

    public ByteArrayDataInput input() {
        return byteArrayDataInput;
    }

    public byte[] bytes() {
        if (byteArrayDataOutput == null) {
            throw new RuntimeException();
        }
        return byteArrayDataOutput.toByteArray();
    }

    public interface Writeable {

        void write(final ByteBuffer byteBuffer);

    }

    public interface Adapter<T> {
        Adapter<String> STRING = Impl.of(ByteArrayDataInput::readUTF, ByteArrayDataOutput::writeUTF);
        Adapter<Byte[]> BYTE_ARRAY = Impl.of(input -> {
            int length = input.readInt();
            Byte[] bytes = new Byte[length];
            for (int i = 0; i < length; i++) {
                bytes[i] = input.readByte();
            }
            return bytes;
        }, (output, bytes) -> {
            output.writeInt(bytes.length);
            for (Byte aByte : bytes) {
                output.writeByte(aByte);
            }
        });
        Adapter<Long> LONG = Impl.of(ByteArrayDataInput::readLong, ByteArrayDataOutput::writeLong);
        Adapter<Double> DOUBLE = Impl.of(ByteArrayDataInput::readDouble, ByteArrayDataOutput::writeDouble);
        Adapter<Float> FLOAT = Impl.of(ByteArrayDataInput::readFloat, ByteArrayDataOutput::writeFloat);
        Adapter<Integer> INT = Impl.of(ByteArrayDataInput::readInt, ByteArrayDataOutput::writeInt);
        Adapter<Identifier> IDENTIFIER = Impl.of(input -> {
            String[] parts = input.readUTF().split(":");
            return Identifier.of(parts[0], parts[1]);
        }, (output, identifier) -> output.writeUTF(identifier.string()));
        Adapter<Deployment> DEPLOYMENT = Impl.of(input -> {
            String[] parts = input.readUTF().split("%");
            return Deployment.of(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        }, (output, deployment) -> output.writeUTF(deployment.name() + "%" + deployment.image() + "%" + deployment.minReplicas() + "%" + deployment.maxReplicas() + "%" + deployment.playerInstanceThreshold()));
        Adapter<Version> VERSION = Impl.of(input -> {
            String[] parts = input.readUTF().split("\\.");
            return Version.major(Integer.parseInt(parts[0])).minor(Integer.parseInt(parts[1])).patch(parts[2]);
        }, (output, version) -> output.writeUTF(version.version()));
        Adapter<Integer> UNSIGNED_BYTE = Impl.of(ByteArrayDataInput::readUnsignedByte, ByteArrayDataOutput::writeInt);
        Adapter<BrokerAgreement> BROKER_AGREEMENT = Impl.of(input -> {
            BrokerAgreement agreement = BrokerAgreement.brokerAgreement();
            int interceptSize = input.readInt();
            for (int i = 0; i < interceptSize; i++) {
                agreement.intercept(new BrokerAgreement.MessageReference.InterceptMessageReference<>(ServiceFile.find(input.readUTF())));
            }
            int monitorSize = input.readInt();
            for (int i = 0; i < monitorSize; i++) {
                agreement.monitor(new BrokerAgreement.MessageReference.MonitorMessageReference<>(ServiceFile.find(input.readUTF())));
            }
            return agreement;
        }, (output, agreement) -> {
            output.writeInt(agreement.interceptSize());
            for (BrokerAgreement.MessageReference.InterceptMessageReference<? extends Message> intercept : agreement.interceptMessages()) {
                output.writeUTF(intercept.message().getName());
            }
            output.writeInt(agreement.monitorSize());
            for (BrokerAgreement.MessageReference.MonitorMessageReference<? extends Message> monitor : agreement.monitorMessages()) {
                output.writeUTF(monitor.message().getName());
            }
        });
        Adapter<Boolean> BOOLEAN = Impl.of(ByteArrayDataInput::readBoolean, ByteArrayDataOutput::writeBoolean);
        Adapter<Byte> BYTE = Impl.of(ByteArrayDataInput::readByte, (output, aByte) -> output.writeByte((int) aByte));
        Adapter<Short> SHORT = Impl.of(ByteArrayDataInput::readShort, (output, aShort) -> output.writeShort((int) aShort));
        Adapter<DataObject<?>> DATA_OBJECT = Impl.of(input -> {
            String identifier = input.readUTF();
            Adapter<?> adapter = Adapter.VALUES.values().stream().filter(a -> a.getClass().getTypeParameters()[0].getBounds()[0].getTypeName().equals(identifier)).findAny().orElseThrow();
            return new DataObject<>(adapter, adapter.read(input));
        }, (output, dataObject) -> {
            output.writeUTF(dataObject.type().getClass().getTypeParameters()[0].getBounds()[0].getTypeName());
            Adapter<Object> adapter = (Adapter<Object>)dataObject.type();
            adapter.write(output, dataObject.value());
        });
        Adapter<UUID> UUID = Impl.of(input -> new UUID(input.readLong(), input.readLong()), (output, uuid) -> {
            output.writeLong(uuid.getMostSignificantBits());
            output.writeLong(uuid.getLeastSignificantBits());
        });
        Adapter<org.jglrxavpok.hephaistos.nbt.NBT> NBT = Impl.of(
            input -> {
                NBTReader nbtReader = new NBTReader(new InputStream() {
                    @Override
                    public int read() {
                        return input.readByte() & 0xFF;
                    }
                }, CompressedProcesser.NONE);
                try {
                    return nbtReader.read();
                } catch (IOException | NBTException e) {
                    throw new RuntimeException(e);
                }
            }, (output, value) -> {
                    NBTWriter nbtWriter = new NBTWriter(new OutputStream() {
                        @Override
                        public void write(int b) {
                            output.writeByte((byte) b);
                        }
                    }, CompressedProcesser.NONE);
                    try {
                        nbtWriter.writeNamed("", value);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            });
        Adapter<Item<?>> ITEM = Impl.of(input -> {
            if (!input.readBoolean()) {
                return Item.empty();
            }
            int material = ProtocolUtil.readVarInt(input);
            int amount = (int) input.readByte();
            return Item.item(material, amount);
        }, ((output, item) -> {
            output.writeBoolean(item != null);
            if (item == null) {
                return;
            }
            ProtocolUtil.writeVarInt(output, (int) item.material());
            output.writeByte(item.amount());
            if (item.nbt().isEmpty()) {
                output.writeByte(0);
                return;
            }
            ByteBuffer byteBuffer = ByteBuffer.empty();
            byteBuffer.write(NBT, item.nbt());
            output.write(byteBuffer.bytes());
        }));
        Adapter<Integer> VAR_INT = Impl.of(ProtocolUtil::readVarInt, ProtocolUtil::writeVarInt);
        Adapter<Long> VAR_LONG = Impl.of(ProtocolUtil::readVarLong, ProtocolUtil::writeVarLong);
        Values<Adapter<?>> VALUES = Values.of(STRING, LONG, DOUBLE, FLOAT, INT, IDENTIFIER, DEPLOYMENT, UNSIGNED_BYTE, BOOLEAN, BYTE, SHORT, DATA_OBJECT, UUID, NBT, ITEM, VAR_INT, VAR_LONG);
        T read(ByteArrayDataInput byteArrayDataInput);
        void write(ByteArrayDataOutput output, T object);
        final class Impl<V> implements Adapter<V> {
            private final Function<ByteArrayDataInput, V> readFunction;
            private final BiConsumer<ByteArrayDataOutput, V> writeConsumer;
            private Impl(Function<ByteArrayDataInput, V> readFunction, BiConsumer<ByteArrayDataOutput, V> writeConsumer) {
                this.readFunction = readFunction;
                this.writeConsumer = writeConsumer;
            }
            public static <V> Impl<V> of(Function<ByteArrayDataInput, V> readFunction, BiConsumer<ByteArrayDataOutput, V> writeConsumer) {
                return new Impl<>(readFunction, writeConsumer);
            }
            @Override
            public V read(ByteArrayDataInput byteArrayDataInput) {
                return readFunction.apply(byteArrayDataInput);
            }
            @Override
            public void write(ByteArrayDataOutput output, V object) {
                writeConsumer.accept(output, object);
            }
        }

    }
}