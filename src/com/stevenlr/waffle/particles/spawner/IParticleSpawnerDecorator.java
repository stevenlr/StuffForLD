/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;
import com.stevenlr.waffle.particles.ParticleSystem;

public interface IParticleSpawnerDecorator {

	boolean canSpawnParticle();
	boolean isDoneSpawning();
	Particle spawnParticle();
	void setParticleSystem(ParticleSystem system);
}
