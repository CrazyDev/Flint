/*
 * New BSD License (BSD-new)
 *
 * Copyright (c) 2015 Maxim Roncacé
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     - Neither the name of the copyright holder nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.caseif.flint.arena;

import net.caseif.flint.component.Component;
import net.caseif.flint.component.ComponentOwner;
import net.caseif.flint.component.exception.OrphanedComponentException;
import net.caseif.flint.exception.rollback.RollbackException;
import net.caseif.flint.lobby.LobbySign;
import net.caseif.flint.lobby.type.ChallengerListingLobbySign;
import net.caseif.flint.lobby.type.StatusLobbySign;
import net.caseif.flint.metadata.MetadataHolder;
import net.caseif.flint.minigame.Minigame;
import net.caseif.flint.round.LifecycleStage;
import net.caseif.flint.round.Round;
import net.caseif.flint.util.physical.Boundary;
import net.caseif.flint.util.physical.Location3D;

import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.LinkedHashSet;

/**
 * Represents an arena in which rounds may take place.
 *
 * @author Max Roncacé
 * @since 1.0
 */
@SuppressWarnings("DuplicateThrows")
public interface Arena extends MetadataHolder, ComponentOwner, Component<Minigame> {

    /**
     * Gets the {@link Minigame} this {@link Arena} is owned by.
     *
     * <p><strong>Note:</strong> This a convenience method for
     * {@link Arena#getOwner()}.</p>
     *
     * @return The {@link Minigame} this {@link Arena} is owned by
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Minigame getMinigame() throws OrphanedComponentException;

    /**
     * Gets the identifier of this {@link Arena}.
     *
     * @return The identifier of this {@link Arena}
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    String getId() throws OrphanedComponentException;

    /**
     * Gets the "friendly" name of this {@link Arena}, as displayed to users.
     *
     * @return The "friendly" name of this {@link Arena}, as displayed to users,
     *     or its ID if one is not set
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    String getName() throws OrphanedComponentException;

    /**
     * Gets the name of the world which contains this {@link Arena}.
     *
     * @return The name of the world which contains this {@link Arena}
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    String getWorld() throws OrphanedComponentException;

    /**
     * Gets the {@link Boundary} which this {@link Arena} is contained within.
     *
     * @return The {@link Boundary} which this {@link Arena} is contained
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     *     within
     * @since 1.0
     */
    Boundary getBoundary() throws OrphanedComponentException;

    /**
     * Sets the {@link Boundary} which this {@link Arena} is contained within.
     *
     * @param bound The new {@link Boundary} which this {@link Arena} is to be
     *     contained within
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    void setBoundary(Boundary bound) throws OrphanedComponentException;

    /**
     * Returns an {@link ImmutableMap} of points at which players may spawn
     * upon entering this arena, mapped to their respective IDs.
     *
     * @return An immutable {@link BiMap} of points at which players may spawn
     *     upon entering this arena, mapped to their respective IDs
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    ImmutableMap<Integer, Location3D> getSpawnPoints() throws OrphanedComponentException;

    /**
     * Adds the given {@link Location3D} to this {@link Arena}'s possible
     * spawn points.
     *
     * @param spawn The {@link Location3D} to add as a new spawn point
     * @return The index of the new spawn point
     * @throws IllegalArgumentException If the spawn point is not contained by
     *     the {@link Arena}'s {@link Boundary}, or if its world differs from
     *     the {@link Arena}'s
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    int addSpawnPoint(Location3D spawn) throws IllegalArgumentException, OrphanedComponentException;

    /**
     * Removes the spawn point of the given index from this {@link Arena}.
     *
     * @param index The index of the spawn point to remove
     * @throws IllegalArgumentException If a spawn point at the given index does
     *     not exist
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    void removeSpawnPoint(int index) throws OrphanedComponentException;

    /**
     * Removes the spawn point(s) at the given location from this {@link Arena}.
     *
     * @param location The location of the spawn point(s) to remove
     * @throws IllegalArgumentException If no spawn points are defined at the
     *     given location
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    void removeSpawnPoint(Location3D location) throws OrphanedComponentException;

    /**
     * Gets the {@link Round} contained by this {@link Arena}.
     *
     * @return The {@link Round} contained by this {@link Arena}
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Optional<Round> getRound() throws OrphanedComponentException;

    /**
     * Creates a new {@link Round} in this {@link Arena}.
     *
     * @param stages A {@link LinkedHashSet} containing {@link LifecycleStage}s
     *               to define for the new {@link Round}
     * @return The newly created {@link Round}
     * @throws IllegalArgumentException If {@code stages} is {@code null} or
     *     contains an empty set
     * @throws IllegalStateException If a {@link Round} already exists in this
     *     {@link Arena}
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Round createRound(ImmutableSet<LifecycleStage> stages)
            throws IllegalArgumentException, IllegalStateException, OrphanedComponentException;

    /**
     * Creates a new {@link Round} in this {@link Arena} with the default
     * {@link LifecycleStage}s.
     *
     * @return The newly created {@link Round}
     * @throws IllegalStateException If a {@link Round} already exists in this
     *     {@link Arena}, or if the default {@link LifecycleStage}s have not
     *     been set as a config option
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Round createRound() throws IllegalStateException, OrphanedComponentException;

    /**
     * Attempts to get the {@link Round} contained by this arena, or if not
     * present, creates and returns a new one with the given lifecycle stages.
     *
     * @param stages A {@link LinkedHashSet} containing {@link LifecycleStage}s
     *               to define for the new {@link Round}
     * @return The retrieved or newly-created {@link Round}
     * @throws IllegalArgumentException If {@code stages} is {@code null} or
     *     contains an empty set
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     */
    Round getOrCreateRound(ImmutableSet<LifecycleStage> stages) throws IllegalArgumentException,
            OrphanedComponentException;

    /**
     * Attempts to get the {@link Round} contained by this arena, or if not
     * present, creates and returns a new one with the default lifecycle stages.
     *
     * @return The retrieved or newly-created {@link Round}
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     */
    Round getOrCreateRound() throws OrphanedComponentException;

    /**
     * Gets an {@link ImmutableList} of {@link LobbySign}s registered for this
     * {@link Arena}.
     *
     * @return An {@link ImmutableList} of {@link LobbySign}s registered for
     *     this {@link Arena}
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    ImmutableList<LobbySign> getLobbySigns() throws OrphanedComponentException;

    /**
     * Gets the {@link LobbySign} at the given {@link Location3D}, if present.
     *
     * @param location The {@link Location3D location} at which to search for a
     *     {@link LobbySign}
     * @return The {@link LobbySign} at the given {@link Location3D}, or
     *     {@link Optional#absent()} if none is registered
     * @throws IllegalArgumentException If {@code location} does not define a world
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Optional<LobbySign> getLobbySignAt(Location3D location) throws IllegalArgumentException, OrphanedComponentException;

    /**
     * Attempts to register a {@link StatusLobbySign} at the given
     * {@link Location3D location}.
     *
     * @param location The {@link Location3D location} to register the sign at
     * @return The newly-registered {@link StatusLobbySign}, or
     *     {@link Optional#absent()} if the passed location does not contain a
     *     sign type or already contains a registered {@link LobbySign}
     * @throws IllegalArgumentException If {@code location} does not define a world
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Optional<StatusLobbySign> createStatusLobbySign(Location3D location)
            throws IllegalArgumentException, OrphanedComponentException;

    /**
     * Attempts to register a {@link ChallengerListingLobbySign} at the given
     * {@link Location3D location}.
     *
     * @param location The {@link Location3D location} to register the sign at
     * @param index The index of the sign (see
     *     {@link ChallengerListingLobbySign#getIndex()} for more info
     * @return The newly-registered {@link StatusLobbySign}, or
     *     {@link Optional#absent()} if the passed location does not contain a
     *     sign type or already contains a registered {@link LobbySign}
     * @throws IllegalArgumentException If {@code location} does not define a world
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    Optional<ChallengerListingLobbySign> createChallengerListingLobbySign(Location3D location, int index)
            throws IllegalArgumentException, OrphanedComponentException;

    /**
     * Explicitly marks a location for rollback. When the arena is rolled back,
     * the location will be reset to its state upon this method being invoked.
     *
     * <p>Note: This will have no effect if a rollback record already exists at
     * the location.</p>
     *
     * @param location The {@link Location3D} to mark for rollback
     * @throws IllegalArgumentException If the provided location is not
     *     contained by this {@link Arena}'s {@link Boundary}
     * @throws RollbackException If an exception occurs while marking the
     *     location for rollback
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.1
     */
    void markForRollback(Location3D location) throws IllegalArgumentException, RollbackException,
            OrphanedComponentException;

    /**
     * Rolls this {@link Arena}'s its state immediately prior to a {@link Round}
     * having been created in it.
     *
     * @throws IllegalStateException If no {@link Round} has taken place in this
     *     {@link Arena} since the last call to this method upon it
     * @throws OrphanedComponentException If this object is orphaned (see
     *     {@link Component} for details)
     * @since 1.0
     */
    void rollback() throws IllegalStateException, OrphanedComponentException;

}
