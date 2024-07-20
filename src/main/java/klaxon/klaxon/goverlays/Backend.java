package klaxon.klaxon.goverlays;

/**
 * As of right now, it's not possible to run GT5NH and HBM together, and GT6 doesn't have pollution, so there's no point
 * supporting multiple backends in parallel. My hope is that if GT6 adds pollution it'll cooperate with NTM so I don't
 * have to make another one...
 */
public enum Backend {
    GT5U,
    HBM_NTM, // shhhh
    NONE
}
