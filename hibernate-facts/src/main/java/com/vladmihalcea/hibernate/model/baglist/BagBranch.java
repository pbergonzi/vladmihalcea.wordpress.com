/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vladmihalcea.hibernate.model.baglist;

import com.vladmihalcea.hibernate.model.util.EntityVisitor;
import com.vladmihalcea.hibernate.model.util.Identifiable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BagBranch - BagBranch
 *
 * @author Vlad Mihalcea
 */
@Entity
public class BagBranch implements Identifiable {

    public static EntityVisitor<BagBranch, BagTree> ENTITY_VISITOR = new EntityVisitor<BagBranch, BagTree>(BagBranch.class) {
        @Override
        public BagTree getParent(BagBranch visitingObject) {
            return visitingObject.getTree();
        }

        @Override
        public List<BagBranch> getChildren(BagTree parent) {
            return parent.getBranches();
        }

        @Override
        public void setChildren(BagTree parent) {
            parent.setBranches(new ArrayList<BagBranch>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    public BagTree tree;

    private int index;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", orphanRemoval = true)
    private List<BagLeaf> leaves = new ArrayList<BagLeaf>();

    public Long getId() {
        return id;
    }

    public BagTree getTree() {
        return tree;
    }

    public void setTree(BagTree tree) {
        this.tree = tree;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<BagLeaf> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<BagLeaf> leaves) {
        this.leaves = leaves;
    }

    public void addLeaf(BagLeaf leaf) {
        leaf.setBranch(this);
        getLeaves().add(leaf);
    }

    public void removeLeaf(BagLeaf leaf) {
        leaf.setBranch(null);
        getLeaves().remove(leaf);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(index);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BagBranch)) {
            return false;
        }
        BagBranch that = (BagBranch) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(index, that.index);
        return eb.isEquals();
    }
}
