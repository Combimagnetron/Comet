package me.combimagnetron.comet.ai;

import me.combimagnetron.comet.ai.response.Response;
import me.combimagnetron.comet.ai.decision.Decision;
import me.combimagnetron.comet.ai.decision.DecisionProvider;
import me.combimagnetron.comet.ai.environment.Environment;
import me.combimagnetron.comet.ai.response.ResponseCurve;

public interface AiEntity {

    Response next();

    ResponseCurve responseCurve();

    DecisionProvider decisionProvider();

    Decision probableDecision();

    Environment environment();

}
