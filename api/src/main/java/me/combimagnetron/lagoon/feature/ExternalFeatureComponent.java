package me.combimagnetron.lagoon.feature;

import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.service.Action;
import me.combimagnetron.lagoon.service.Service;
import me.combimagnetron.lagoon.service.config.Parameter;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;

public abstract class ExternalFeatureComponent implements Service {
    private final Feature feature;
    private final OutsourceReason outsourceReason;

    public ExternalFeatureComponent(Feature feature, OutsourceReason outsourceReason) {
        this.feature = feature;
        this.outsourceReason = outsourceReason;
    }

    public Feature feature() {
        return feature;
    }

    public OutsourceReason outsourceReason() {
        return outsourceReason;
    }

    public enum OutsourceReason {
        VERSION_DEPENDENT_IMPLEMENTATION, EXTERNAL_SERVICE, MULTI_PLATFORM_FEATURE
    }

    public static final class FeatureOutsourceReasonParameter implements Parameter<Feature, OutsourceReason> {
        private Feature first;
        private OutsourceReason second;
        private final Path location;

        public FeatureOutsourceReasonParameter(Feature first, OutsourceReason second, Path location) {
            this.first = first;
            this.second = second;
            this.location = location;
        }

        @Override
        public Feature first() {
            return first;
        }

        @Override
        public OutsourceReason second() {
            return second;
        }

        @Override
        public void first(Feature feature) {
            this.first = feature;
        }

        @Override
        public void second(OutsourceReason outsourceReason) {
            this.second = outsourceReason;
        }

        @Override
        public Path location() {
            return location;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (FeatureOutsourceReasonParameter) obj;
            return Objects.equals(this.first, that.first) &&
                    Objects.equals(this.second, that.second) &&
                    Objects.equals(this.location, that.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, location);
        }

        @Override
        public String toString() {
            return "FeatureOutsourceReasonParameter[" +
                    "first=" + first + ", " +
                    "second=" + second + ", " +
                    "location=" + location + ']';
        }


        }
}
