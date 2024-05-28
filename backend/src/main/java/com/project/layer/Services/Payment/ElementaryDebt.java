package com.project.layer.Services.Payment;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class ElementaryDebt {
    protected ElementaryDebt next;

    public ElementaryDebt(){

    }

    public ElementaryDebt(ElementaryDebt next) {
        this.next = next;
    }

    public boolean hasNext(){
        return getNext() != null;
    }

    public abstract void debt(Pay pay);
}
