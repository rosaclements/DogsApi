package rvc.entities;

import lombok.Getter;

/**An enum representing reasons a police dog might have left service.*/
public enum LeavingReason
{
    TRANSFERRED("Transferred"),
    REJECTED("Rejected"),
    RETIRED_REHOUSED("Retired (rehoused)"),
    RETIRED_PUT_DOWN("Retired (put down)"),
    KIA("KIA"),
    DIED("Died");

    @Getter
    private final String name;

    LeavingReason(String name)
    {
        this.name = name;
    }
}
